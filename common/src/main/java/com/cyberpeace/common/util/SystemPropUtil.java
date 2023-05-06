package com.cyberpeace.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 系统工具
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public class SystemPropUtil {

	/**
	 * 系统环境变量工具
	 */
	private SystemPropUtil() {}

	/**
	 * 读取数据。
	 * 优先从jvm变量取，如果取不到，再从环境变量获取
	 * @param key
	 * @return
	 */
	public static String getProp(String key) {
		// 1、先从jvm获取变量
		String value = System.getProperty(key);

		// 2、取不到，再从环境变量获取
		if (StringUtils.isBlank(value)) {
			value = System.getenv(key);
		}

		return value;
	}
}
