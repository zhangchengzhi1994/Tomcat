package tomcat_ex01;

import java.io.IOException;
import java.io.InputStream;

/**
 * 表示一个HTTP请求，通过Socket对象获取输入流，读取HTTP原始数据
 * */
public class Request {
	private InputStream inputStream;
	private String urlString;
	
	public Request(InputStream inputStream){
		this.inputStream = inputStream;
	}
	
	/**
	 * 解析HTTP的原始数据，赋值给类对象
	 * */
	public void parse() {
		StringBuffer stringBuffer = new StringBuffer(2048);
		int i;
		byte[] buffer = new byte[2048];
		
		try {
			i = inputStream.read(buffer);  //字节流的int表示
		} catch (IOException e) {
			e.printStackTrace();
			i=-1;
		}
		for (int j = 0; j < i; j++) { //？？？
			stringBuffer.append((char)buffer[j]);  
		}
		System.out.print(stringBuffer.toString());//字符串变量
		urlString = parseUrl(stringBuffer.toString()); //取得HTTP中的资源值
	}
	
	/**
	 * 获得请求的资源名
	 * @param requestString
	 * @return
	 */
	private String parseUrl(String requestString){
		int index1,index2;
		index1 = requestString.indexOf(' '); //返回第一个空格的路径
		if (index1 != -1) {
			index2 = requestString.indexOf(' ',index1+1); //第二个空格的路径
			if (index2>index1) {
				return requestString.substring(index1+1,index2); //url的值
			}
		}
		return null;
	}
	
	/**
	 * 获取什么URL
	 * @return
	 */
	public String getUrl() {
		return urlString;
	}
}
