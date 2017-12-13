package tomcat_connector_http;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

import tomcat_ex02.Request;

public class HttpConnector implements Runnable {
	boolean stoped;
	private String scheme = "http";

	public String getScheme() {
		return scheme;
	}

	@Override
	public void run() {
		ServerSocket serverSocket = null;
		int port = 8080;
		try{ //先创建一个serverdocket实例
			serverSocket = new 
					ServerSocket(port,1,InetAddress.getByName("127.0.0.1"));
		}catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		//Loop waiting for a request
		while (!stoped) {
			Socket socket = null;
			try {
				socket = serverSocket.accept();          //等待client的相应，返回一个socket
			}catch (Exception e) {
				continue;
			}
			HttpProcessor processor = new HttpProcessor();
			processor.process(socket);
		}
	}
	
	public void start() {
		Thread thread = new Thread(this);
		thread.start();
		
	}
}
