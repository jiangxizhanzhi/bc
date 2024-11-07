package com.cyberpeace.common.threadlocal;

/**
 * 线程本地上下文
 *
 * @author SN
 * @date 2024/11/07
 */
public class ThreadLocalContext {

	/**
	 * 白色更新线程本地
	 */
	public static final ThreadLocal<Boolean> whiteUpdateThreadLocal = ThreadLocal
			.withInitial(() -> false);

	/**
	 * 线程本地上下文
	 */
	private ThreadLocalContext() {}
}
