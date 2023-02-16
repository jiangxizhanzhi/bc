package com.cyberpeace.integratation.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.cyberpeace.common.util.SystemPropUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class YhEnvService {

	private String YH_HOST;

	private String YH_PORT;

	private String YH_URL;

	public String getYhHost() {
		return YH_URL;
	}

	@PostConstruct
	public void init() {
		YH_HOST = SystemPropUtil.getProp("YH_HOST");
		YH_PORT = SystemPropUtil.getProp("YH_PORT");
		YH_URL = "http://" + YH_HOST + ":" + YH_PORT;

		log.info("yh host=[{}], port=[{}]", YH_HOST, YH_PORT);
	}
}
