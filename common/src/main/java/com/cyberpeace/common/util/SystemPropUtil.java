package com.cyberpeace.common.util;

import org.apache.commons.lang3.StringUtils;

public class SystemPropUtil {

	private SystemPropUtil() {}

	/**
	 * 读取环境变量数据。
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

	/**
	 * 是否可以导出内置资源
	 * @return
	 */
	public static Boolean getExportBuiltin() {
		if (SystemPropUtil.getProp("EXPORT_BUILTIN") == null || !Boolean.parseBoolean(SystemPropUtil.getProp("EXPORT_BUILTIN"))) {
			return false;
		}
		return true;
	}
}
