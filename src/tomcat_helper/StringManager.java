package tomcat_helper;


import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class StringManager {
	private ResourceBundle bundle;

	/**
	 * ����StringManager�����Map,�԰���Ϊkey,valueΪStringManager����
	 */
	private static Map<String, StringManager> stringManagers = new HashMap<String, StringManager>();

	/**
	 * ˽�л��Ĺ��캯��
	 * 
	 * @param packageName
	 *            ����
	 */
	private StringManager(String packageName) {
		// ������LocalStrings,��Ҳ��Ϊʲô���ǿ�����Դ�ļ�����LocalStrings������ԭ��
		String baseName = packageName + ".LocalStrings";
		// ���ݰ�������ȡ��Դ�ļ�
		bundle = ResourceBundle.getBundle(baseName);
	}

	/**
	 * ����StringManagerʵ���ľ�̬������ȷ����ͬ�İ���������ͬ��ʵ�� ͬ������
	 * 
	 * @param packageName
	 *            ����
	 * @return
	 */
	public synchronized static StringManager getStringManager(String packageName) {
		// �ȴ�map�в���
		StringManager stringManager = stringManagers.get(packageName);

		// �����Ӧ����StringManagerδʵ��������ʵ���������ҷ���Map�л���
		if (stringManager == null) {
			stringManager = new StringManager(packageName);
			stringManagers.put(packageName, stringManager);
		}
		return stringManager;
	}

	// ============����ΪStringManager��������쳣��Ϣ�ķ���===========
	public String getString(String key) {
		// �Բ����Ƚ�����֤
		if (key == null) {
			String msg = "key is null";
			throw new NullPointerException(msg);
		}
		String result = null;
		try {
			result = bundle.getString(key);
		} catch (MissingResourceException e) {
			result = "can not find message with the key " + key;
		}

		return result;
	}

	public String getString(String key, Object[] args) {
		String result = null;
		String initMessage = getString(key);

		try {
			Object[] notNullArgs = args;
			for (int i = 0; i < args.length; i++) {
				if (args[i] == null) {
					if (notNullArgs == args)
						notNullArgs = (Object[]) args.clone();
					args[i] = "null";
				}
			}
			// MessageFormat��ʹ��
			result = MessageFormat.format(initMessage, notNullArgs);
		}
		// �����쳣�Ĵ���ֵ������ѧϰ
		// ���ƴ󲿷ֵĳ���Ա����ֱ����һ��iae.printStackTrace();��
		catch (IllegalArgumentException iae) {
			StringBuilder sb = new StringBuilder();
			sb.append(initMessage);
			for (int i = 0; i < args.length; i++) {
				sb.append(" arg[" + i + "]=" + args[i]);
			}
			result = sb.toString();

		}
		return result;
	}

	// �����Ƿ��������أ�����ͻ��˵ĵ���
	public String getString(String key, Object arg) {
		Object[] args = new Object[] { arg };
		return getString(key, args);
	}

	public String getString(String key, Object arg1, Object arg2) {
		Object[] args = new Object[] { arg1, arg2 };
		return getString(key, args);
	}

	public String getString(String key, Object arg1, Object arg2, Object arg3) {
		Object[] args = new Object[] { arg1, arg2, arg3 };
		return getString(key, args);
	}

	public String getString(String key, Object arg1, Object arg2, Object arg3, Object arg4) {
		Object[] args = new Object[] { arg1, arg2, arg3, arg4 };
		return getString(key, args);
	}

	public static void main(String[] args) {
		StringManager stringManager = StringManager.getStringManager("ex03.pyrmont.connector.http");
		String string = stringManager.getString("httpConnector.alreadyInitialized");

		System.out.println(string);

		string = stringManager.getString("httpConnector.anAddress", "192.165.23.26", 12);
		System.out.println(string);

	}
}
