package com.cyberpeace.common.threadlocal;

public class ThreadLocalContext {

	public static final ThreadLocal<Boolean> whiteUpdateThreadLocal = ThreadLocal
			.withInitial(() -> false);

	// 定时任务里面的数据需要存储
	public static final ThreadLocal<Boolean> whiteUpdateJobThreadLocal = ThreadLocal
			.withInitial(() -> false);

	private ThreadLocalContext() {}
}
