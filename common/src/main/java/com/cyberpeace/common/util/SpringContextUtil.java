package com.cyberpeace.common.util;

import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.Nullable;

/**
 * 上下文工具
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public class SpringContextUtil implements ApplicationContextAware {

	/**
	 * Spring应用上下文环境
	 */
	private static ApplicationContext applicationContext;

	/**
	 * 让应用程序上下文
	 *
	 * @return {@link ApplicationContext}
	 */
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
		SpringContextUtil.applicationContext = applicationContext;
	}

	/**
	 * 获取实例
	 *
	 * @param name 名字
	 * @return {@link T}
	 * @throws BeansException 豆子异常
	 */
	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) throws BeansException {
		return (T) applicationContext.getBean(name);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBeanByClass(Class<T> cls) throws BeansException {
		return (T) applicationContext.getBean(cls);
	}

	/**
	 * 得到类型bean
	 *
	 * @param type 类型
	 * @return {@link Map}<{@link String}, {@link T}>
	 * @throws BeansException 豆子异常
	 */
	public static <T> Map<String, T> getBeansOfType(@Nullable Class<T> type) throws BeansException {
		return applicationContext.getBeansOfType(type);
	}
}
