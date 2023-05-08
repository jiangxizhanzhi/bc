package com.cyberpeace.common.model;

/**
 * httpclient上下文
 * httpclient调用时需要的上下文
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public class HttpclientContext {

	/**
	 * 上下文
	 */
	private static final ThreadLocal<HttpclientModel> context = new ThreadLocal<HttpclientModel>();

	private static String errorMsg = null;

	/**
	 * 获得上下文
	 *
	 * @return {@link HttpclientModel}
	 */
	public static HttpclientModel getContext() {
		return context.get();
	}

	/**
	 * 设置上下文
	 *
	 * @param model 模型
	 */
	public static void setContext(HttpclientModel model) {
		context.set(model);
	}

	/**
	 * 删除背景
	 */
	public static void removeContext() {
		context.remove();
	}

	/**
	 * 设置错误味精
	 *
	 * @param msg 味精
	 */
	public static void setErrorMsg(String msg) {
		errorMsg = msg;
	}

	/**
	 * 得到错误味精
	 *
	 * @return {@link String}
	 */
	public static String getErrorMsg() {
		return errorMsg;
	}
}