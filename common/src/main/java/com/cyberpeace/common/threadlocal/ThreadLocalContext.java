package com.cyberpeace.common.threadlocal;

public class ThreadLocalContext {

	public static final ThreadLocal<Boolean> whiteUpdateThreadLocal = ThreadLocal
			.withInitial(() -> false);

	private ThreadLocalContext() {}
}
