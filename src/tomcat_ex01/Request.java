package tomcat_ex01;

import java.io.IOException;
import java.io.InputStream;

/**
 * ��ʾһ��HTTP����ͨ��Socket�����ȡ����������ȡHTTPԭʼ����
 * */
public class Request {
	private InputStream inputStream;
	private String urlString;
	
	public Request(InputStream inputStream){
		this.inputStream = inputStream;
	}
	
	/**
	 * ����HTTP��ԭʼ���ݣ���ֵ�������
	 * */
	public void parse() {
		StringBuffer stringBuffer = new StringBuffer(2048);
		int i;
		byte[] buffer = new byte[2048];
		
		try {
			i = inputStream.read(buffer);  //�ֽ�����int��ʾ
		} catch (IOException e) {
			e.printStackTrace();
			i=-1;
		}
		for (int j = 0; j < i; j++) { //������
			stringBuffer.append((char)buffer[j]);  
		}
		System.out.print(stringBuffer.toString());//�ַ�������
		urlString = parseUrl(stringBuffer.toString()); //ȡ��HTTP�е���Դֵ
	}
	
	/**
	 * ����������Դ��
	 * @param requestString
	 * @return
	 */
	private String parseUrl(String requestString){
		int index1,index2;
		index1 = requestString.indexOf(' '); //���ص�һ���ո��·��
		if (index1 != -1) {
			index2 = requestString.indexOf(' ',index1+1); //�ڶ����ո��·��
			if (index2>index1) {
				return requestString.substring(index1+1,index2); //url��ֵ
			}
		}
		return null;
	}
	
	/**
	 * ��ȡʲôURL
	 * @return
	 */
	public String getUrl() {
		return urlString;
	}
}
