/**
 * 这段代码定义了一个工具类 `GuavaCacheUtil`，用于基于 Google Guava 提供的缓存功能创建缓存实例。以下是代码的简要分析： 
 * 1. **类设计**： 
 * - 类名为 `GuavaCacheUtil`，使用了单例模式（私有构造函数），确保该类不能被实例化。 
 * 2. **静态方法**： 
 * - 方法 `newInstance` 是一个泛型方法，支持指定键值类型 `<K, V>`。 
 * - 参数： 
 * - `duration`：设置缓存条目在最后一次访问后过期的时间长度。 
 * - `unit`：定义 `duration` 的时间单位（如秒、分钟等）。 
 * - `kClass` 和 `vClass`：键和值的类型（虽然未在实现中实际使用，但可以通过泛型约束限制类型）。 
 * - 功能：通过 `CacheBuilder` 配置并返回一个具有访问时间过期策略的缓存实例。 
 * 3. **核心逻辑**： 
 * - 使用 `CacheBuilder.newBuilder()` 创建缓存构建器。 
 * - 调用 `.expireAfterAccess(duration, unit)` 设置缓存条目在指定时间内无访问即失效。 
 * - 最终调用 `.build()` 返回一个 `Cache<K, V>` 实例。 
 * 4. **潜在问题**： 
 * - 当前实现仅设置了访问时间过期策略，未设置写入时间过期或最大容量限制。 
 * - 参数 `kClass` 和 `vClass` 虽然作为输入，但没有实际用途，可以移除以简化代码。 
 * 总结：该工具类提供了一个简单的方法来创建基于访问时间的 Guava 缓存实例，适合需要动态控制缓存过期时间的场景。 
**/
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
