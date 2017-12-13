package tomcat_ex02;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;



public class Response implements ServletResponse {
	private static final int BUFFER_SIZE = 1024;
	Request request;
	OutputStream outputStream;
	PrintWriter writer;
	
	/**
	 * 构造函数传入输出流
	 * @param outputStream
	 */
	public Response(OutputStream outputStream) {
		this.outputStream = outputStream;
	}
	
	/**
	 * 接收一个request对象，并让全类使用
	 * @param request
	 */
	public void setRequest(Request request){
		this.request = request;
	}
	
	/**
	 * 发送一个静态的资源到客户端
	 * @throws IOException
	 */
	public void sendStaticResource() throws IOException{
		byte[] bytes = new byte[BUFFER_SIZE];
		FileInputStream fileInputStream = null;
		try {
			File file = new File(HttpServer.WEB_ROOT,request.getUrl()); //params 路径，文件名
			//获得文件流，将文件流一点点的“输出流”
			if (file.exists()) {
				fileInputStream = new FileInputStream(file); 		    //文件输入流
				int ch = fileInputStream.read(bytes,0, BUFFER_SIZE);    //第一行流转byte
				while (ch!=-1) {
					outputStream.write(bytes, 0, ch);                   //写入输出流
					ch = fileInputStream.read(bytes, 0, BUFFER_SIZE);   //貌似是换到第二行吧?
				}
			}else {
				//file not found
				String errorString = "HTTP/1.1 404 File Not Found\r\n" +
						"Content-Type: text/html\r\n"+
						"\r\n"+
						"<h1>File Not Found</h1>";
				outputStream.write(errorString.getBytes());
			}
		} catch (Exception e) {
			// thrown if cannot instantiate a File object
			System.out.println(e.toString());
		}finally {
			//关闭输出流，流文件正式存入socket
			if (fileInputStream!=null) {
				fileInputStream.close(); //关闭流
			}
		}
	}
	
	
	@Override
	public void flushBuffer() throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public int getBufferSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getCharacterEncoding() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getContentType() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Locale getLocale() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ServletOutputStream getOutputStream() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PrintWriter getWriter() throws IOException {
		writer = new PrintWriter(outputStream,true);
		return writer;
	}

	@Override
	public boolean isCommitted() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void reset() {
		// TODO Auto-generated method stub

	}

	@Override
	public void resetBuffer() {
		// TODO Auto-generated method stub

	}

	@Override
	public void setBufferSize(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setCharacterEncoding(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setContentLength(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setContentType(String arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setLocale(Locale arg0) {
		// TODO Auto-generated method stub

	}

}
