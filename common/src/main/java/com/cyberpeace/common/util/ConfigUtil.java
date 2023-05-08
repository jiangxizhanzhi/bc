package com.cyberpeace.common.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.google.common.collect.Maps;

/**
 * 配置工具
 *
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public class ConfigUtil extends PropertyPlaceholderConfigurer {

	private static Map<String, String> ctxPropertiesMap;

	/**
	 * 获取指定属性的属性值
	 *
	 * @param name
	 * @return
	 */
	public static String getContextProperty(String name) {
		return ctxPropertiesMap.get(name);
	}

	/**
	 * 查找key以指定前缀（prefix）开头的所有属性
	 *
	 * @param prefix key的前缀
	 * @return 返回符合条件的所有属性，map的key为属性名、value为属性值
	 */
	public static Map<String, String> getSubPropertiesMap(String prefix) {
		Map<String, String> subMap = Maps.newHashMap();

		for (Map.Entry<String, String> entry : ctxPropertiesMap.entrySet()) {
			if (StringUtils.startsWith(entry.getKey(), prefix)) {
				subMap.put(entry.getKey(), entry.getValue());
			}
		}

		return subMap;
	}

	/**
	 * 流程属性
	 *
	 * @param beanFactoryToProcess bean工厂处理
	 * @param props                道具
	 * @throws BeansException 豆子异常
	 */
	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess,
			Properties props) throws BeansException {
		super.processProperties(beanFactoryToProcess, props);
		ctxPropertiesMap = new HashMap<>();
		for (Object key : props.keySet()) {
			String keyStr = key.toString();
			String value = props.getProperty(keyStr);
			ctxPropertiesMap.put(keyStr, value);
		}
	}

}
