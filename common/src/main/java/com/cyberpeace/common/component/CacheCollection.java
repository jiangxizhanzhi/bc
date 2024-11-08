package com.cyberpeace.common.component;

import com.cyberpeace.common.model.cache.RtksMatch;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 缓存集合
 *
 * @author SN
 * @date 2024/11/08
 */
@Component
public class CacheCollection {

	/**
	 * RTKS态势缓存
	 */
	private static Cache<String, RtksMatch> rtksSituationCache = CacheBuilder.newBuilder()
			.expireAfterWrite(10, TimeUnit.MINUTES).build();

	/**
	 * 获取态势缓存
	 *
	 * @return {@code Cache<String, RtksMatch> }
	 */
	public Cache<String, RtksMatch> getSituationCache() {
		return rtksSituationCache;
	}
}
