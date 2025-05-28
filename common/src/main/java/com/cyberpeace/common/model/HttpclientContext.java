/**
 * 这段代码定义了一个线程安全的上下文类 `HttpclientContext`，用于在 HTTP 客户端调用中存储和管理上下文信息。以下是代码的简要分析： 
 * 1. **功能概述**： 
 * - `HttpclientContext` 提供了线程隔离的上下文管理机制，确保每个线程都有自己独立的上下文数据。 
 * 2. **核心特性**： 
 * - 使用 `ThreadLocal` 实现线程隔离，`context` 是一个静态变量，每个线程都有自己的副本。 
 * - 提供了三个主要方法： 
 * - `getContext()`：获取当前线程的上下文对象。 
 * - `setContext(HttpclientModel model)`：设置当前线程的上下文对象。 
 * - `removeContext()`：移除当前线程的上下文对象。 
 * 3. **设计目的**： 
 * - 适用于多线程环境下需要独立上下文场景，例如 HTTP 请求中的认证信息、请求头等。 
 * 4. **潜在问题**： 
 * - 必须注意线程结束后调用 `removeContext()`，避免内存泄漏或上下文污染。 
 * - 如果 `HttpclientModel` 的引用过大或持有外部资源，可能影响性能。 
 * 总结：这是一个简单且实用的线程上下文管理工具类，适合在多线程 HTTP 调用中使用。 
**/
package com.cyberpeace.common.model;

/**
 * httpclient调用时需要的上下文
 *
 * @author SN
 * @date 2024/11/08
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