package com.cyberpeace.common.model;

import org.apache.commons.lang3.StringUtils;

import com.cyberpeace.common.constant.WebConstant;

/**
 * 常见上下文
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public class CommonContext {

	private static final ThreadLocal<String> languageContext = new ThreadLocal();

	private static final ThreadLocal<String> ipContext = new ThreadLocal();

	private static final ThreadLocal<String> urlContext = new ThreadLocal();

	private static final ThreadLocal<String> authorizationContext = new ThreadLocal();

	/**
	 * 常见上下文
	 */
	private CommonContext() {}

	/**
	 * 获得知识产权
	 *
	 * @return {@link String}
	 */
	public static String getIp() {
		return ipContext.get();
	}

	public static void setIp(String ip) {
		ipContext.set(ip);
	}

	/**
	 * 删除ip
	 */
	public static void removeIp() {
		ipContext.remove();
	}

	/**
	 * 获取url
	 *
	 * @return {@link String}
	 */
	public static String getUrl() {
		return urlContext.get();
	}

	/**
	 * 设置网址
	 *
	 * @param url url
	 */
	public static void setUrl(String url) {
		urlContext.set(url);
	}

	/**
	 * 删除网址
	 */
	public static void removeUrl() {
		urlContext.remove();
	}

	/**
	 * 获得语言
	 *
	 * @return {@link String}
	 */
	public static String getLanguage() {
		String language = languageContext.get();
		if (language != null) {
			return language;
		}
		return WebConstant.LANGUAGE_ZH;
	}

	/**
	 * 设置语言
	 *
	 * @param language 语言
	 */
	public static void setLanguage(String language) {
		languageContext.set(language);
	}

	/**
	 * 设置授权
	 *
	 * @param authorization 授权
	 */
	public static void setAuthorization(String authorization) {
		authorizationContext.set(authorization);
	}

	/**
	 * 得到授权
	 *
	 * @return {@link String}
	 */
	public static String getAuthorization() {
		return authorizationContext.get();
	}

	/**
	 * 移除授权
	 */
	public static void removeAuthorization() {
		authorizationContext.remove();
	}

	/**
	 * 消除语言
	 */
	public static void removeLanguage() {
		languageContext.remove();
	}

	/**
	 * 是中文
	 *
	 * @return boolean
	 */
	public static boolean isChinese() {
		return StringUtils.equals(CommonContext.getLanguage(), WebConstant.LANGUAGE_ZH);
	}
}
