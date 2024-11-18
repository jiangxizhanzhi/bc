package com.cyberpeace.common.util;

import org.apache.commons.lang3.StringUtils;

/**
 * 系统配置util
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/18
 * @since 1.0.0
 */
public class SystemPropUtil {

	/**
	 * 系统配置util
	 *
	 * @throws
	 */
	private SystemPropUtil() {}

	/**
	 * 读取环境变量数据。
	 * 优先从jvm变量取，如果取不到，再从环境变量获取
	 *
	 * @param key
	 * @return
	 * @throws
	 * @see String
	 * @see String
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
