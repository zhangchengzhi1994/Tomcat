package tomcat_ex02;

/**
 * ����̬��Դ
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
