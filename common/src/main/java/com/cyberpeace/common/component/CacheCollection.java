package com.cyberpeace.common.component;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.cyberpeace.common.model.cache.RtksMatch;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/** 缓存集合
 * @author cyberpeace
 * @date 2023/05/06
 */
@Component
public class CacheCollection {

	/**
	 *
	 */
	private static Cache<String, RtksMatch> rtksSituationCache = CacheBuilder.newBuilder()
			.expireAfterWrite(10, TimeUnit.MINUTES).build();

	/**
	 * 得到情况缓存
	 *
	 * @return {@link Cache}<{@link String}, {@link RtksMatch}>
	 */
	public Cache<String, RtksMatch> getSituationCache() {
		return rtksSituationCache;
	}
}
