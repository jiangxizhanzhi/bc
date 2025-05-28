/**
 * 这段代码定义了一个名为 `CacheCollection` 的 Spring 组件类，用于管理缓存集合。以下是代码的简要分析： 
 * 1. **类与注解**： 
 * - 类 `CacheCollection` 被标记为 `@Component`，表明它是一个 Spring 管理的 Bean，可以在 Spring 容器中被注入和使用。 
 * 2. **缓存定义**： 
 * - 使用了 Google Guava 库中的 `CacheBuilder` 来创建一个缓存实例 `rtksSituationCache`。 
 * - 缓存类型为 `<String, RtksMatch>`，即键为字符串，值为 `RtksMatch` 对象。 
 * - 缓存设置了过期策略：数据在写入后 10 分钟自动过期。 
 * 3. **方法**： 
 * - 提供了一个公共方法 `getSituationCache()`，用于返回 `rtksSituationCache` 缓存实例。 
 * 总结：该类主要用于集中管理 `RtksMatch` 对象的缓存，并通过 Spring 注入的方式提供缓存实例的访问接口。 
**/
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
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
@Component
public class CacheCollection {

	/**
	 * RTKS态势缓存
	 *
	 * @see Cache<String, RtksMatch>
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
