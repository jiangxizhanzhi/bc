package com.cyberpeace.common.threadlocal;

/**
 * 线程本地上下文
 *
 * @author SN
 * @date 2024/11/07
 * @version 1.0.0
 * @since 1.0.0
 */
public class ThreadLocalContext {

	/**
	 * 白色更新线程本地
	 *
	 * @see ThreadLocal<Boolean>
	 */
	public static final ThreadLocal<Boolean> whiteUpdateThreadLocal = ThreadLocal
			.withInitial(() -> false);

	/**
	 * 线程本地上下文
	 *
	 * @throws
	 */
	private ThreadLocalContext() {}
}
