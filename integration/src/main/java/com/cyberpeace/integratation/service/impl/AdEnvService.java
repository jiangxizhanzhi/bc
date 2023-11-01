package com.cyberpeace.integratation.service.impl;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.util.SystemPropUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class AdEnvService {

	private String AD_HOST;

	private String AD_URL;

	public String getAdHost() {
		if (StringUtils.isBlank(AD_HOST)) {
			return CommonConstant.EMPTY_STRING;
		}
		return AD_URL;
	}

	@PostConstruct
	public void init() {
		AD_HOST = SystemPropUtil.getProp("AD_HOST");
		String AD_PORT = SystemPropUtil.getProp("AD_PORT");
		AD_URL = "http://" + AD_HOST + ":" + AD_PORT;

		log.info("ad host=[{}], port=[{}]", AD_HOST, AD_PORT);
	}
}
