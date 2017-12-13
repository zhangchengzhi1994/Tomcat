package tomcat_ex02;

/**
 * 处理静态资源
 * @author Administrator
 *
 */
public class StaticResourceProcessor {
	public void process(Request request,Response response) {
		try {
			response.sendStaticResource();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
