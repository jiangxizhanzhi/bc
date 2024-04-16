package com.cyberpeace.integratation.service.impl;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.util.SystemPropUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Slf4j
@Service
public class SupersetEnvService {

	private String SUPERSET_HOST;

	private String SUPERSET_PORT;

	private String SUPERSET_URL;

	public String getSupersetHost() {
		if (StringUtils.isBlank(SUPERSET_HOST)) {
			return CommonConstant.EMPTY_STRING;
		}
		return SUPERSET_URL;
	}

	@PostConstruct
	public void init() {
		SUPERSET_HOST = SystemPropUtil.getProp("SUPERSET_HOST");
		SUPERSET_PORT = SystemPropUtil.getProp("SUPERSET_PORT");
		SUPERSET_URL = "http://" + SUPERSET_HOST + ":" + SUPERSET_PORT;

		log.info("ad host=[{}], port=[{}]", SUPERSET_HOST, SUPERSET_PORT);
	}
}
