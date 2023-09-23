package com.cyberpeace.common.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

public class GuavaCacheUtil {

	private GuavaCacheUtil() {}

	public static <K, V> Cache<K, V> newInstance(long duration, TimeUnit unit, Class<K> kClass,
			Class<V> vClass) {
		return CacheBuilder.newBuilder().expireAfterAccess(duration, unit).build();
	}

}
