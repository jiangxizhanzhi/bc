package com.cyberpeace.integratation.service.impl;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.util.SystemPropUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class HikEnvService {

	private String HIK_HOST;

	private String HIK_PORT;

	private String HIK_URL;

	public String getHIKHost() {
		if (StringUtils.isBlank(HIK_HOST)) {
			return CommonConstant.EMPTY_STRING;
		}
		return HIK_URL;
	}

	@PostConstruct
	public void init() {
		HIK_HOST = SystemPropUtil.getProp("HIK_HOST");
		HIK_PORT = SystemPropUtil.getProp("HIK_PORT");
		HIK_URL = "http://" + HIK_HOST + ":" + HIK_PORT;

		log.info("HIK host=[{}], port=[{}]", HIK_HOST, HIK_PORT);
	}
}
