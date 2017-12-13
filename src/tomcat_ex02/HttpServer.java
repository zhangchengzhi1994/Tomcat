package tomcat_ex02;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;


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
		try{ //先创建一个serverdocket实例
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
				socket = serverSocket.accept();          //等待client的相应，返回一个socket
				input = socket.getInputStream();         //取得socket的输入流
				outputStream = socket.getOutputStream(); //取得socket的输出流
				
				//Create Request object andparams
				Request request = new Request(input);    //将输入流注入req实例
				request.parse();  					     //为实例取得资源路径
				
				//create Response object
				Response response = new Response(outputStream);//将输出流给resp
				response.setRequest(request);				   //将res实例注入resp
//				response.sendStaticResource();                 //对象获得一个输出流
				
				//check if this is a request for a servlet or
				//if ture inject res&resp
				if (request.getUrl().startsWith("/servlet")) {
					servletProcessor1 processor1 = new servletProcessor1();
					processor1.process(request, response);
				}
				
				
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
