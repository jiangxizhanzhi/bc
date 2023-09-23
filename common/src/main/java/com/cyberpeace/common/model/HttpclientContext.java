package com.cyberpeace.common.model;

/**
 * httpclient调用时需要的上下文
 */
public class HttpclientContext {

	private static final ThreadLocal<HttpclientModel> context = new ThreadLocal<HttpclientModel>();

	public static HttpclientModel getContext() {
		return context.get();
	}

	public static void setContext(HttpclientModel model) {
		context.set(model);
	}

	public static void removeContext() {
		context.remove();
	}

	private HttpclientContext() {}
}