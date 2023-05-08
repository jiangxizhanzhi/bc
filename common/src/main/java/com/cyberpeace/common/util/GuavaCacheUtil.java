package com.cyberpeace.common.util;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * 缓存工具
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public class GuavaCacheUtil {

	public static <K, V> Cache<K, V> newInstance(long duration, TimeUnit unit, Class<K> kClass,
			Class<V> vClass) {
		return CacheBuilder.newBuilder().expireAfterAccess(duration, unit).build();
	}

}
