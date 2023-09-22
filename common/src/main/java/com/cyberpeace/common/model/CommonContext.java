package com.cyberpeace.common.model;

import org.apache.commons.lang3.StringUtils;

import com.cyberpeace.common.constant.WebConstant;

public class CommonContext {

	private static final ThreadLocal<String> languageContext = new ThreadLocal<>();
	private static final ThreadLocal<String> ipContext = new ThreadLocal<>();
	private static final ThreadLocal<String> authorizationContext = new ThreadLocal<>();

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
}
