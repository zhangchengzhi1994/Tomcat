package tomcat_ex01;

import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.NonReadableChannelException;

import javax.print.attribute.standard.OutputDeviceAssigned;

import tomcat_ex01.*;


public class HttpServer {
	public static final String WEB_ROOT=System.getProperty("user.dir")+File.separator+"111";
	
	//shutdown command
	private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
	
	//the shutdown command received
	private boolean SHUTDOWN = false;
	
	public static void main(String[] args) {
		HttpServer server = new HttpServer();
		server.await();
	}
	
	/**
	 * 
	 */
	public void await() {
		ServerSocket serverSocket = null;
		int port = 8080;
		try{ //�ȴ���һ��serverdocketʵ��
			serverSocket = new ServerSocket(port,1,InetAddress.getByName("127.0.0.1"));
		}catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
		
		//Loop waiting for a request
		while (!SHUTDOWN) {
			Socket socket = null;
			InputStream input = null;
			OutputStream outputStream = null;
			
			try {
				socket = serverSocket.accept(); //����һ��socket
				input = socket.getInputStream(); //ȡ��socket��������
				outputStream = socket.getOutputStream(); //ȡ��socket�������
				
				//Create Request object andparams
				Request request = new Request(input); 
				request.parse(); //ȡ��Դֵ
				
				//create Response object
				Response response = new Response(outputStream);
				response.setRequest(request);
				response.sendStaticResource(); //������һ�������
				
				//close the socket
				socket.close();
				
				//check if the previous URL is a shutdown command
				SHUTDOWN = request.getUrl().equals(SHUTDOWN_COMMAND);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
		}
	}
}
