package com.cyberpeace.scene.service;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.SystemPropUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CpEnvService {

	private String CP_HOST;

	public String getCpHost() {
		return CP_HOST;
	}

	@PostConstruct
	public void init() {

		String cpHost = SystemPropUtil.getProp("CP_HOST");
		String cpPort = ConfigUtil.getContextProperty("cp.port");
		if (StringUtils.isBlank(cpHost)) {
			cpHost = CommonConstant.LOCALHOST;
		}
		CP_HOST = "http://" + cpHost + ":" + cpPort;

		log.info("cp host=[{}], port=[{}]", cpHost, cpPort);
	}
}
