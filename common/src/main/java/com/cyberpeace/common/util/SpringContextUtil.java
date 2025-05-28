/**
 * 这段代码定义了一个工具类 `SpringContextUtil`，用于在 Spring 应用中获取 `ApplicationContext` 及其管理的 Bean。以下是代码的简要分析： 
 * 1. **功能概述**： 
 * - 该类实现了 `ApplicationContextAware` 接口，目的是在 Spring 容器启动时自动注入 `ApplicationContext`。 
 * - 提供了一系列静态方法，方便在非 Spring 管理的类或方法中获取 Spring 容器中的 Bean。 
 * 2. **关键特性**： 
 * - **`applicationContext` 静态变量**：存储 Spring 的 `ApplicationContext` 实例，供其他地方使用。 
 * - **`setApplicationContext` 方法**：实现 `ApplicationContextAware` 接口的方法，用于初始化 `applicationContext`。 
 * - **`getApplicationContext` 方法**：返回当前的 `ApplicationContext`。 
 * - **`getBean` 方法**：根据 Bean 名称获取指定类型的 Bean。 
 * - **`getBeanByClass` 方法**：根据 Bean 类型获取指定的 Bean。 
 * - **`getBeansOfType` 方法**：获取所有符合指定类型的 Bean。 
 * 3. **注意事项**： 
 * - 此类适用于需要在非 Spring 管理的类中获取 Bean 的场景，但应谨慎使用，避免过度耦合。 
 * - 在多线程环境中需确保 `applicationContext` 的线程安全性。 
 * 总体而言，这段代码提供了一个便捷的方式来访问 Spring 容器中的 Bean，但在实际开发中应尽量减少直接依赖此类，以保持代码的可维护性和解耦性。 
**/
package com.cyberpeace.common.util;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.Nullable;

/**
 * Spring 上下文实用程序
 *
 * @author SN
 * @date 2024/11/08
 */
public class SpringContextUtil implements ApplicationContextAware {

	/**
	 * Spring应用上下文环境
	 */
	private static ApplicationContext applicationContext;

	/**
	 * 获取行为依赖上下文
	 *
	 * @return {@code ApplicationContext }
	 */
	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 实现了ApplicationContextAware 接口，必须实现该方法；
	 * 通过传递applicationContext参数初始化成员变量applicationContext
	 *
	 * @param applicationContext
	 * @throws BeansException bean 异常
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		SpringContextUtil.applicationContext = applicationContext;
	}

	/**
	 * 获取豆
	 *
	 * @param name 名称
	 * @return {@code T }
	 * @throws BeansException bean 异常
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) throws BeansException {
		return (T) applicationContext.getBean(name);
	}

	/**
	 * 获取豆由类
	 *
	 * @param cls CLS
	 * @return {@code T }
	 * @throws BeansException bean 异常
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBeanByClass(Class<T> cls) throws BeansException {
		return (T) applicationContext.getBean(cls);
	}

	/**
	 * 获取豆之类型
	 *
	 * @param type 类型
	 * @return {@code Map<String, T> }
	 * @throws BeansException bean 异常
	 */
	public static <T> Map<String, T> getBeansOfType(@Nullable Class<T> type) throws BeansException {
		return applicationContext.getBeansOfType(type);
	}
}
