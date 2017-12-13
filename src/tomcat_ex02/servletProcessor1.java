package tomcat_ex02;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


/**
 * �����servlet��Դ��HTTP����
 * @author Administrator
 *
 */
public class servletProcessor1 {
	//get url by request
	//get Class by URLClassLoad 
	//new "target" instance
	//call service method
	public void process(Request request, Response response) {
		String urlString = request.getUrl(); //��ȡURL
		String servletString = urlString.substring(urlString.lastIndexOf("/")+1);//��ȡservlet����
		URLClassLoader loader = null;

		try {
			//create a URLClassLoader
			URL[] urls = new URL[1];
			URLStreamHandler streamHandler = null;
			File classFile = new File(Constants.WEB_ROOT); //��ó�����ļ�����
			
			String repository = (new URL(
					"file",null,classFile.getCanonicalPath()+
					File.separator)).toString(); //��·��
			
			urls[0] = new URL(null, repository, streamHandler);//�������·����URL
			loader = new URLClassLoader(urls);                 //�κ���/������URL���ܶ�ȡ
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		Class myClass = null;
		
		try {
			myClass = loader.loadClass(servletString); //�����ࣨ�Ѿ�������ģ�
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		Servlet servlet = null;
		RequestFacade requestFacade = new RequestFacade(request);
		ResponseFacade responseFacade = new ResponseFacade(response);
		try {
			servlet = (Servlet) myClass.newInstance();//newһ���µ�ʵ��
			servlet.service(requestFacade, responseFacade);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

}
