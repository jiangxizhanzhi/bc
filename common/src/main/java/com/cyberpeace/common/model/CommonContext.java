package com.cyberpeace.common.model;

public class CommonContext {

	private static final ThreadLocal<String> languageContext = new ThreadLocal();
	private static final ThreadLocal<String> ipContext = new ThreadLocal();

	private CommonContext() {}

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
		return languageContext.get();
	}

	public static void setLanguage(String language) {
		languageContext.set(language);
	}

	public static void removeLanguage() {
		languageContext.remove();
	}
}
