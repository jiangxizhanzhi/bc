package com.cyberpeace.cr.crbase.constant;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.ConfigUtil;

class ConfigReader {

	public static String read(String name) {
		String config = ConfigUtil.getContextProperty(name);
		if (config == null) {
			throw new BusinessException(ErrorCode.NOT_CONFIGURED, name);
		}
		return config;
	}

	public static Integer readInt(String name) {
		String config = read(name);
		try {
			return new Integer(config);
		} catch (Exception e) {
			throw new BusinessException(ErrorCode.INVALID_CONFIGURATION, name, config);
		}
	}
}
