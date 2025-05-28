package com.cyberpeace.config.custom;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import com.cyberpeace.common.util.SystemPropUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 服务启动，依赖的参数校验。
 * <p>
 * <p>
 * <p>
 * 目的在于快速发现可能的配置项错误！
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
@Slf4j
@Component
public class InitChecker {

	/**
	 * @throws
	 */
	@PostConstruct
	public void checkList() {
		checkMp();

		checkCr();

		checkOj();

		checkRtks();
	}

	/**
	 * 靶场启动项检查
	 *
	 * @throws
	 */
	private void checkCr() {}

	/**
	 * 实训启动项检查
	 *
	 * @throws
	 */
	private void checkOj() {}

	/**
	 * rtks启动项检查
	 *
	 * @throws
	 */
	private void checkRtks() {}

	/**
	 * 检查中台配置
	 *
	 * @throws
	 */
	private void checkMp() {
		blankCheck("MP_HOST");
		blankCheck("MP_PORT");
		blankCheck("MP_SECRET");

		// 额外校验中台设置项
		String mpSecret = SystemPropUtil.getProp("MP_SECRET");
		String[] mpSecretSplit = StringUtils.split(mpSecret, ",");

		Integer mpSecretLength = 2;
		if (null == mpSecretSplit || mpSecretSplit.length != mpSecretLength) {
			// 格式错误
			log.error("[MP_SECRET]wrong format, read value=[{}]", mpSecret);
			throw new Error();
		}
	}

	/**
	 * 空白支票
	 *
	 * @param field 田
	 * @throws
	 * @see String
	 */
	private void blankCheck(String field) {
		String value = SystemPropUtil.getProp(field);

		if (StringUtils.isBlank(value)) {
			log.error("[{}]not set!", field);
			throw new Error();
		} else {
			log.info("prop=[{}], value=[{}]", field, value);
		}
	}
}
