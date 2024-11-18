package com.cyberpeace.common.util;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

import java.util.concurrent.TimeUnit;

/**
 * Guava Cache 实用程序
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
public class GuavaCacheUtil {

	/**
	 * 新建实例
	 *
	 * @param duration 期间
	 * @param unit     单位
	 * @param kClass   K 级
	 * @param vClass   V 级
	 * @return {@code Cache<K, V> }
	 * @throws
	 * @see long
	 * @see TimeUnit
	 * @see Class<K>
	 * @see Class<V>
	 * @see Cache<K, V>
	 */
	public static <K, V> Cache<K, V> newInstance(long duration, TimeUnit unit, Class<K> kClass,
			Class<V> vClass) {
		return CacheBuilder.newBuilder().expireAfterAccess(duration, unit).build();
	}

}
