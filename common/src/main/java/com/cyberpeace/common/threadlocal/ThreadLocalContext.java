package com.cyberpeace.common.threadlocal;

/**
 * 线程本地上下文
 *
 * @author SN
 * @date 2024/11/08
 */
public class ThreadLocalContext {

	/**
	 * 白色更新线程本地
	 */
	public static final ThreadLocal<Boolean> whiteUpdateThreadLocal = ThreadLocal
			.withInitial(() -> false);

	private ThreadLocalContext() {}
}
