package com.cyberpeace.config.custom;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.cyberpeace.common.util.SystemPropUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 服务启动，依赖的参数校验。
 *
 * 目的在于快速发现可能的配置项错误！
 */
@Slf4j
@Component
public class InitChecker {

	@PostConstruct
	public void checkList() {
		// 1、校验中台配置
		checkMp();
	}

	/**
	 * 检查中台配置
	 */
	private void checkMp() {
		blankCheck("MP_HOST");
		blankCheck("MP_PORT");
		blankCheck("MP_SECRET");

		// 额外校验中台设置项
		String mpSecret = SystemPropUtil.getProp("MP_SECRET");
		String[] mpSecretSplit = StringUtils.split(mpSecret, ",");

		if (null == mpSecretSplit || mpSecretSplit.length != 2) {
			log.error("[MP_SECRET]格式错误, 读到的值=[{}]", mpSecret);
			throw new Error();
		}
	}

	private void blankCheck(String field) {
		String value = SystemPropUtil.getProp(field);

		if (StringUtils.isBlank(value)) {
			log.error("[{}]未设置！", field);
			throw new Error();
		} else {
			log.info("prop=[{}], value=[{}]", field, value);
		}
	}
}
