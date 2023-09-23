package com.cyberpeace.common.cache;

import java.util.concurrent.TimeUnit;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

public class CrRoleCache {
    public static LoadingCache<String, Object> cache = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(60, TimeUnit.SECONDS).build(new CacheLoader<String, Object>() {
        public Object load(String key)  {
            return null;
        }
    });
    
	private CrRoleCache() {}
}
