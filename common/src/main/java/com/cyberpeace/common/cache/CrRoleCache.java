package com.cyberpeace.common.cache;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import java.util.concurrent.TimeUnit;

/**
 * 靶场岗位缓存
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
public class CrRoleCache {
    /**
     * 缓存
     *
     * @see LoadingCache<String, Object>
     */
    public static LoadingCache<String, Object> cache = CacheBuilder.newBuilder().maximumSize(1000).expireAfterWrite(60, TimeUnit.SECONDS).build(new CacheLoader<String, Object>() {
        public Object load(String key)  {
            return null;
        }
    });
}
