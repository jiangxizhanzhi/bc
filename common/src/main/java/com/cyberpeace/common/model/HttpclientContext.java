package com.cyberpeace.common.model;

/**
 * httpclient调用时需要的上下文
 *
 * @author SN
 * @date 2024/11/07
 */
public class HttpclientContext {

	/**
	 * 上下文
	 */
	private static final ThreadLocal<HttpclientModel> context = new ThreadLocal<HttpclientModel>();

	/**
	 * 获取上下文
	 *
	 * @return {@code HttpclientModel }
	 */
	public static HttpclientModel getContext() {
		return context.get();
	}

	/**
	 * 设置上下文
	 *
	 * @param model 型
	 */
	public static void setContext(HttpclientModel model) {
		context.set(model);
	}

	/**
	 * 删除上下文
	 */
	public static void removeContext() {
		context.remove();
	}
}