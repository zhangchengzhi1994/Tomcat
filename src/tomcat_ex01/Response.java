package tomcat_ex01;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.swing.tree.DefaultTreeCellEditor.EditorContainer;

/**
 * 
 * @author zcz
 * @deprecated HTTP�ķ���
 */
public class Response {
	private static final int BUFFER_SIZE = 1024;
	Request request;
	OutputStream outputStream;
	
	/**
	 * ���캯�����������
	 * @param outputStream
	 */
	public Response(OutputStream outputStream) {
		this.outputStream = outputStream;
	}
	
	/**
	 * ����һ��request���󣬲���ȫ��ʹ��
	 * @param request
	 */
	public void setRequest(Request request){
		this.request = request;
	}
	
	/**
	 * ����һ����̬����Դ���ͻ���
	 * @throws IOException
	 */
	public void sendStaticResource() throws IOException{
		byte[] bytes = new byte[BUFFER_SIZE];
		FileInputStream fileInputStream = null;
		try {
			File file = new File(HttpServer.WEB_ROOT,request.getUrl()); //params ·�����ļ���
			if (file.exists()) {
				fileInputStream = new FileInputStream(file); //�ļ�������
				int ch = fileInputStream.read(bytes,0, BUFFER_SIZE); //��һ����תbyte
				while (ch!=-1) {
					outputStream.write(bytes, 0, ch);   //д�������
					ch = fileInputStream.read(bytes, 0, BUFFER_SIZE); //ò���ǻ����ڶ��а�?
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
			if (fileInputStream!=null) {
				fileInputStream.close(); //�ر���
			}
		}
	}
}
