package com.cyberpeace.integratation.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.cyberpeace.common.util.SystemPropUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 中台环境Service
 *
 * @author SN
 * @date 2024/11/11
 */
@Slf4j
@Service
public class MpEnvService {

	/**
	 * 中台主机
	 */
	private String MP_HOST;

	/**
	 * 中台港口
	 */
	private String MP_PORT;

	/**
	 * 中台网址
	 */
	private String MP_URL;

	/**
	 * 获取中台主机
	 *
	 * @return {@code String }
	 */
	public String getMpHost() {
		return MP_URL;
	}

	/**
	 * 初始化
	 */
	@PostConstruct
	public void init() {
		MP_HOST = SystemPropUtil.getProp("MP_HOST");
		MP_PORT = SystemPropUtil.getProp("MP_PORT");
		MP_URL = "http://" + MP_HOST + ":" + MP_PORT;

		log.info("mp host=[{}], port=[{}]", MP_HOST, MP_PORT);
	}
}
