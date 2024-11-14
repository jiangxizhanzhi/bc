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
 * @date 2024/11/14
 */
@Slf4j
@Service
public class AdEnvService {

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
		if (StringUtils.isBlank(AD_URL)) {
			log.warn("compose yml AD_HOST not set");
			return CommonConstant.EMPTY_STRING;
		}
		return AD_URL;
	}

	/**
	 * 初始化
	 */
	@PostConstruct
	public void init() {
		String AD_HOST = SystemPropUtil.getProp("AD_HOST");
		// 竞赛模块不一定要存在
		if (!StringUtils.isBlank(AD_HOST) && !AD_HOST.startsWith("http")) {
			log.error("AD_HOST [{}] not starts with http!", AD_HOST);
			throw new Error();
		}
		// 5.5+ 版本修改，和drm变量共用
		AD_URL = AD_HOST;

		log.info("ad url=[{}]", AD_URL);
	}
}
