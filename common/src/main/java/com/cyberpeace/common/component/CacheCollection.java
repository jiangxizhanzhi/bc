package com.cyberpeace.common.component;

import java.util.concurrent.TimeUnit;

import org.springframework.stereotype.Component;

import com.cyberpeace.common.model.cache.RtksMatch;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

@Component
public class CacheCollection {

	private static Cache<String, RtksMatch> rtksSituationCache = CacheBuilder.newBuilder()
			.expireAfterWrite(10, TimeUnit.MINUTES).build();

	public Cache<String, RtksMatch> getSituationCache() {
		return rtksSituationCache;
	}
}
