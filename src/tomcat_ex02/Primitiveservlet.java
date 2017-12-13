package tomcat_ex02;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;

public class Primitiveservlet implements Servlet {

	@Override
	public void destroy() {
		System.out.println("destory");
	}

	@Override
	public ServletConfig getServletConfig() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getServletInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void init(ServletConfig arg0) throws ServletException {
		System.out.println("init");
	}

	@Override
	public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {
		System.out.println("--Service--");
		PrintWriter outPrintWriter = arg1.getWriter();
		outPrintWriter.print("service1");
		outPrintWriter.print("service2");
	}

}
