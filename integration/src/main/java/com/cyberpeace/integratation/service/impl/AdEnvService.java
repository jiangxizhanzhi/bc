package com.cyberpeace.integratation.service.impl;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.util.SystemPropUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 竞赛环境Service
 *
 * @author SN
 * @date 2024/11/08
 */
@Slf4j
@Service
public class AdEnvService {

	/**
	 * 竞赛主机
	 */
	private String AD_HOST;

	/**
	 * 竞赛港口
	 */
	private String AD_PORT;

	/**
	 * 竞赛网址
	 */
	private String AD_URL;

	/**
	 * 获取竞赛主机
	 *
	 * @return {@code String }
	 */
	public String getAdHost() {
		if (StringUtils.isBlank(AD_HOST)) {
			return CommonConstant.EMPTY_STRING;
		}
		return AD_URL;
	}

	/**
	 * 初始化
	 */
	@PostConstruct
	public void init() {
		AD_HOST = SystemPropUtil.getProp("AD_HOST");
		AD_PORT = SystemPropUtil.getProp("AD_PORT");
		AD_URL = "http://" + AD_HOST + ":" + AD_PORT;

		log.info("ad host=[{}], port=[{}]", AD_HOST, AD_PORT);
	}
}
