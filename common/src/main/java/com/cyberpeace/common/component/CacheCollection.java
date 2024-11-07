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
 * @date 2024/11/07
 */
@Component
public class CacheCollection {

	/**
	 * RTKS 情况缓存
	 */
	private static Cache<String, RtksMatch> rtksSituationCache = CacheBuilder.newBuilder()
			.expireAfterWrite(10, TimeUnit.MINUTES).build();

	/**
	 * 获取情况缓存
	 *
	 * @return {@code Cache<String, RtksMatch> }
	 */
	public Cache<String, RtksMatch> getSituationCache() {
		return rtksSituationCache;
	}
}
