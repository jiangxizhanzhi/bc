package com.cyberpeace.situation.constant;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.ConfigUtil;
import lombok.extern.slf4j.Slf4j;

/*
 * @BelongsPackage: com.cyberpeace.situation.constant
 * @Author: hhuang
 * @CreateTime: 2025/3/24 11:29
 * @Description: 配置读取器
 * @Version: 1.0
 */
@Slf4j
class ConfigReader {

	/**
	 * 读
	 *
	 * @param name 名称
	 * @return {@code String }
	 */
	public static String read(String name) {
		String config = ConfigUtil.getContextProperty(name);
		if (config == null) {
			log.error("[{}] not configured!", name);
			throw new BusinessException(ErrorCode.NOT_CONFIGURED, name);
		}
		return config;
	}

	/**
	 * 读取 int
	 *
	 * @param name 名称
	 * @return {@code Integer }
	 */
	public static Integer readInt(String name) {
		String config = read(name);
		try {
			return new Integer(config);
		} catch (Exception e) {
			log.error("Invalid configuration for [{}]: [{}]!", name, config);
			throw new BusinessException(ErrorCode.INVALID_CONFIGURATION, name, config);
		}
	}


	/**
	 * @description: 读取布尔类型的配置
	 * @param name
	 * @throws
	 * @return Boolean
	 */
	public static Boolean readBoolean(String name) {
		String config = read(name);
		try {
			return Boolean.valueOf(config);
		} catch (Exception e) {
			log.error("Invalid boolean configuration for [{}]: [{}]!", name, config);
			throw new BusinessException(ErrorCode.INVALID_CONFIGURATION, name, config);
		}
	}

	/**
	 * 配置读取器
	 */
	private ConfigReader() {}
}
