package tomcat_helper;


import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class StringManager {
	private ResourceBundle bundle;

	/**
	 * 保存StringManager对象的Map,以包名为key,value为StringManager对象
	 */
	private static Map<String, StringManager> stringManagers = new HashMap<String, StringManager>();

	/**
	 * 私有化的构造函数
	 * 
	 * @param packageName
	 *            包名
	 */
	private StringManager(String packageName) {
		// 包名加LocalStrings,这也是为什么我们看的资源文件是以LocalStrings命名的原因
		String baseName = packageName + ".LocalStrings";
		// 根据包名，获取资源文件
		bundle = ResourceBundle.getBundle(baseName);
	}

	/**
	 * 返回StringManager实例的静态方法，确保相同的包名返回相同的实例 同步方法
	 * 
	 * @param packageName
	 *            包名
	 * @return
	 */
	public synchronized static StringManager getStringManager(String packageName) {
		// 先从map中查找
		StringManager stringManager = stringManagers.get(packageName);

		// 如果对应包的StringManager未实例化，则实例化，并且放入Map中缓存
		if (stringManager == null) {
			stringManager = new StringManager(packageName);
			stringManagers.put(packageName, stringManager);
		}
		return stringManager;
	}

	// ============以下为StringManager对象查找异常消息的方法===========
	public String getString(String key) {
		// 对参数先进行验证
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
			// MessageFormat的使用
			result = MessageFormat.format(initMessage, notNullArgs);
		}
		// 这里异常的处理值得我们学习
		// 估计大部分的程序员都会直接来一句iae.printStackTrace();吧
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

	// 以下是方法的重载，方便客户端的调用
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
