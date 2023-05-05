package com.cyberpeace.common.model;

/**
 * httpclient调用时需要的上下文
 */
public class HttpclientContext {

	private static final ThreadLocal<HttpclientModel> context = new ThreadLocal<HttpclientModel>();

	private static String errorMsg = null;

	public static HttpclientModel getContext() {
		return context.get();
	}

	public static void setContext(HttpclientModel model) {
		context.set(model);
	}

	public static void removeContext() {
		context.remove();
	}

	public static void setErrorMsg(String msg) {
		errorMsg = msg;
	}

	public static String getErrorMsg() {
		return errorMsg;
	}
}