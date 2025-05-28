/**
 * 这段代码定义了一个名为 `CommonContext` 的类，用于管理线程本地的上下文信息。以下是代码的主要功能和特点： 
 * 1. **线程本地存储**：使用 `ThreadLocal` 来存储不同线程独立的数据，确保每个线程的数据隔离性。 
 * 2. **上下文字段**： 
 * - `languageContext`：存储当前线程的语言上下文，默认值为 `WebConstant.LANGUAGE_ZH`（中文）。 
 * - `ipContext`：存储当前线程的 IP 地址。 
 * - `authorizationContext`：存储当前线程的授权信息。 
 * - `cyberTokenContext`：存储与“30所接口”相关的 token。 
 * 3. **方法功能**： 
 * - 提供了获取、设置和移除各上下文字段的方法。 
 * - `isChinese()` 方法判断当前语言是否为中文。 
 * - 所有上下文字段的操作都是线程安全的，因为使用了 `ThreadLocal`。 
 * 4. **构造函数**：私有构造函数防止实例化，符合工具类的设计规范。 
 * 5. **依赖**：使用了 `org.apache.commons.lang3.StringUtils` 和 `com.cyberpeace.common.constant.WebConstant`，分别用于字符串操作和常量定义。 
 * 总结：该类通过 `ThreadLocal` 实现了线程安全的上下文管理，适用于需要在多线程环境中存储和访问特定上下文数据的场景。 
**/
package com.cyberpeace.common.model;

import org.apache.commons.lang3.StringUtils;

import com.cyberpeace.common.constant.WebConstant;

/**
 * 常见上下文
 *
 * @author SN
 * @date 2024/11/08
 */
public class CommonContext {

	/**
	 * 语言上下文
	 */
	private static final ThreadLocal<String> languageContext = new ThreadLocal();
	/**
	 * ip地址上下文
	 */
	private static final ThreadLocal<String> ipContext = new ThreadLocal();
	/**
	 * 授权上下文
	 */
	private static final ThreadLocal<String> authorizationContext = new ThreadLocal();
	/**
	 * 30所接口token
	 */
	private static final ThreadLocal<String> cyberTokenContext = new ThreadLocal();

	private CommonContext() {
	}

	public static String getIp() {
		return ipContext.get();
	}

	public static void setIp(String ip) {
		ipContext.set(ip);
	}

	public static void removeIp() {
		ipContext.remove();
	}

	public static String getLanguage() {
		String language = languageContext.get();
		if (language != null) {
			return language;
		}
		return WebConstant.LANGUAGE_ZH;
	}

	public static void setLanguage(String language) {
		languageContext.set(language);
	}

	public static void setAuthorization(String authorization) {
		authorizationContext.set(authorization);
	}

	public static String getAuthorization() {
		return authorizationContext.get();
	}

	public static void removeAuthorization() {
		authorizationContext.remove();
	}

	public static void removeLanguage() {
		languageContext.remove();
	}

	public static boolean isChinese() {
		return StringUtils.equals(CommonContext.getLanguage(), WebConstant.LANGUAGE_ZH);
	}

	public static void setCyberToken(String cyberToken) {
		cyberTokenContext.set(cyberToken);
	}

	public static String getCyberToken() {
		return cyberTokenContext.get();
	}

	public static void removeCyberToken() {
		cyberTokenContext.remove();
	}
}
