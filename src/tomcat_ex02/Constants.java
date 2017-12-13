package tomcat_ex02;

import java.io.File;

public class Constants {
	
	public static final String WEB_ROOT=System.getProperty("user.dir")+File.separator+"111";
	
	//shutdown command
	private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
	
	//the shutdown command received
	private boolean SHUTDOWN = false;
	
}
