package tomcat_ex02;

import java.io.File;
import java.net.URL;
import java.net.URLClassLoader;
import java.net.URLStreamHandler;

import javax.servlet.Servlet;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


/**
 * 处理对servlet资源的HTTP请求
 * @author Administrator
 *
 */
public class servletProcessor1 {
	//get url by request
	//get Class by URLClassLoad 
	//new "target" instance
	//call service method
	public void process(Request request, Response response) {
		String urlString = request.getUrl(); //获取URL
		String servletString = urlString.substring(urlString.lastIndexOf("/")+1);//获取servlet类名
		URLClassLoader loader = null;

		try {
			//create a URLClassLoader
			URL[] urls = new URL[1];
			URLStreamHandler streamHandler = null;
			File classFile = new File(Constants.WEB_ROOT); //获得抽象的文件对象
			
			String repository = (new URL(
					"file",null,classFile.getCanonicalPath()+
					File.separator)).toString(); //类路径
			
			urls[0] = new URL(null, repository, streamHandler);//和上面的路径的URL
			loader = new URLClassLoader(urls);                 //任何以/结束的URL都能读取
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		Class myClass = null;
		
		try {
			myClass = loader.loadClass(servletString); //加载类（已经编译过的）
		} catch (Exception e) {
			System.out.println(e.toString());
		}
		
		Servlet servlet = null;
		RequestFacade requestFacade = new RequestFacade(request);
		ResponseFacade responseFacade = new ResponseFacade(response);
		try {
			servlet = (Servlet) myClass.newInstance();//new一个新的实例
			servlet.service(requestFacade, responseFacade);
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}

}
