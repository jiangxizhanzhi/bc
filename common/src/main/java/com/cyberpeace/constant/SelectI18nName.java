package com.cyberpeace.constant;

import com.cyberpeace.common.model.CommonContext;

/**
 * 适用于数据库中保存中英文名称(cnName, enName)再根据上下文取国际化名称的情形
 *
 * @author shazhichuan
 */
/**
 * 该代码定义了一个 Java 接口 `SelectI18nName`，用于处理基于上下文（`CommonContext`）获取国际化名称的功能。以下是简要分析： 
 * 1. **功能描述**： 
 * - 该接口适用于数据库中存储中英文名称（`cnName` 和 `enName`），并通过上下文动态选择返回中文或英文名称。 
 * - 提供了两个抽象方法：`getCnName()` 和 `getEnName()`，分别返回中文和英文名称。 
 * 2. **默认实现**： 
 * - 提供了一个默认方法 `selectI18nName()`，通过调用 `CommonContext.isChinese()` 判断当前上下文语言，返回对应的中文或英文名称。 
 * 3. **依赖关系**： 
 * - 依赖于 `com.cyberpeace.common.model.CommonContext` 类，用于判断当前语言环境。 
 * 4. **适用场景**： 
 * - 适合需要多语言支持且数据存储为中英文的场景，通过上下文动态切换语言。 
 * 总结：这是一个简洁的接口设计，利用默认方法简化了多语言名称的选择逻辑，提高了代码复用性和可维护性。 
**/
public interface SelectI18nName {
	/**
	 * 返回中文名称
	 *
	 * @return 中文名称
	 */
	String getCnName();

	/**
	 * 返回英文名称
	 *
	 * @return 英文名称
	 */
	String getEnName();

	/**
	 * 获取国际化名称的默认方法
	 *
	 * @return 国际化名称
	 */
	default String selectI18nName() {
		return CommonContext.isChinese() ? getCnName() : getEnName();
	}
}

