package com.cyberpeace.integratation.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.cyberpeace.common.util.SystemPropUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 中台环境Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @since 1.0.0
 */
@Slf4j
@Service
public class MpEnvService {

	/**
	 * 中台网址
	 *
	 * @see String
	 */
	private String MP_URL;

	// 往电力靶场跳转的地址
	private String JUMP_URL;

	/**
	 * 获取中台主机
	 *
	 * @return {@code String }
	 * @throws
	 * @see String
	 */
	public String getMpHost() {
		return MP_URL;
	}

	public String getJUMP_URL() {
		return JUMP_URL;
	}

	/**
	 * 初始化
	 *
	 * @throws
	 */
	@PostConstruct
	public void init() {
		String MP_HOST = SystemPropUtil.getProp("MP_HOST");

		if (!MP_HOST.startsWith("http")) {
			log.error("[{}] not starts with http!", MP_HOST);
			throw new Error();
		}
		// 5.5+ 版本修改和drm变量共用
		MP_URL = MP_HOST;
		JUMP_URL = SystemPropUtil.getProp("JUMP_URL");

		log.info("mp url=[{}]", MP_URL);
		log.info("jump url=[{}]", JUMP_URL);
	}
}
