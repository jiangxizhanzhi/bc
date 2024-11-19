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
