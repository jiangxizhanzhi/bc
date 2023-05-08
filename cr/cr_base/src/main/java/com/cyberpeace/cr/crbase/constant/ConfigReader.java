package com.cyberpeace.cr.crbase.constant;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.ConfigUtil;

/**
 * 配置读取
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
class ConfigReader {

	/**
	 * 读
	 *
	 * @param name 名字
	 * @return {@link String}
	 */
	public static String read(String name) {
		String config = ConfigUtil.getContextProperty(name);
		if (config == null) {
			throw new BusinessException(ErrorCode.NOT_CONFIGURED, name);
		}
		return config;
	}

	/**
	 * 读int
	 *
	 * @param name 名字
	 * @return {@link Integer}
	 */
	public static Integer readInt(String name) {
		String config = read(name);
		try {
			return new Integer(config);
		} catch (Exception e) {
			throw new BusinessException(ErrorCode.INVALID_CONFIGURATION, name, config);
		}
	}
}
