package tomcat_startup;

import tomcat_connector.HttpConnector;

public final class Bootstrap {
	public static void main(String[] args) {
		HttpConnector connector = new HttpConnector();
		connector.start();
	}
	
}
