package metasploit;

public abstract interface MetasploitMBean
{
	public abstract String check();
	public abstract String run(String paramString);
	public abstract String upload(String R, String file_name);
}
