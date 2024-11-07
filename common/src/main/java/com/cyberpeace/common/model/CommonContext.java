package com.cyberpeace.common.model;

import org.apache.commons.lang3.StringUtils;

import com.cyberpeace.common.constant.WebConstant;

/**
 * 常见上下文
 *
 * @author SN
 * @date 2024/11/07
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
	 * 常见上下文
	 */
	private CommonContext() {
	}

	/**
	 * 获取ip地址
	 *
	 * @return {@code String }
	 */
	public static String getIp() {
		return ipContext.get();
	}

	/**
	 * 设置ip地址
	 *
	 * @param ip ip地址
	 */
	public static void setIp(String ip) {
		ipContext.set(ip);
	}

	/**
	 * 删除ip地址
	 */
	public static void removeIp() {
		ipContext.remove();
	}

	/**
	 * 获取语言
	 *
	 * @return {@code String }
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
	 * 获取授权
	 *
	 * @return {@code String }
	 */
	public static String getAuthorization() {
		return authorizationContext.get();
	}

	/**
	 * 删除授权
	 */
	public static void removeAuthorization() {
		authorizationContext.remove();
	}

	/**
	 * 删除语言
	 */
	public static void removeLanguage() {
		languageContext.remove();
	}

	/**
	 * 是中国人
	 *
	 * @return boolean
	 */
	public static boolean isChinese() {
		return StringUtils.equals(CommonContext.getLanguage(), WebConstant.LANGUAGE_ZH);
	}
}
