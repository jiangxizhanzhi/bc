package com.cyberpeace.common.util;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.Nullable;

public class SpringContextUtil implements ApplicationContextAware {

	/**
	 * Spring应用上下文环境
	 */
	private static ApplicationContext applicationContext;

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	/**
	 * 实现了ApplicationContextAware 接口，必须实现该方法；
	 * 通过传递applicationContext参数初始化成员变量applicationContext
	 * @param applicationContext
	 * @throws BeansException
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		set(applicationContext);
	}

	public static void set(ApplicationContext applicationContext) {
		SpringContextUtil.applicationContext = applicationContext;
	}
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) throws BeansException {
		return (T) applicationContext.getBean(name);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBeanByClass(Class<T> cls) throws BeansException {
		return  applicationContext.getBean(cls);
	}

	public static <T> Map<String, T> getBeansOfType(@Nullable Class<T> type) throws BeansException {
		return applicationContext.getBeansOfType(type);
	}
}
