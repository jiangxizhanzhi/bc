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
