package com.cyberpeace.common.component;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.cyberpeace.common.model.cache.RtksMatch;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * 缓存集合
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Component
public class CacheCollection {

	/**
	 * RTKS 情况缓存
	 *
	 * @see Cache<String, RtksMatch>
	 */
	private static Cache<String, RtksMatch> rtksSituationCache = CacheBuilder.newBuilder()
			.expireAfterWrite(10, TimeUnit.MINUTES).build();

	/**
	 * 获取情况缓存
	 *
	 * @return {@code Cache<String, RtksMatch> }
	 * @throws
	 * @see Cache<String, RtksMatch>
	 */
	public Cache<String, RtksMatch> getSituationCache() {
		return rtksSituationCache;
	}
}
