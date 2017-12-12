package tomcat_ex01;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.UnknownHostException;
import java.util.jar.Attributes.Name;

public class service {
	public static void main(String[] args) throws IOException {
		InetAddress name = InetAddress.getByName("127.0.0.1");
		ServerSocket socket = new ServerSocket(8080,1,name);
	}
}
