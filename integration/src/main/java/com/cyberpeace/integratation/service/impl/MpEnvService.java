package com.cyberpeace.integratation.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.cyberpeace.common.util.SystemPropUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MpEnvService {

	private String MP_HOST;

	private String MP_PORT;

	private String MP_URL;

	public String getMpHost() {
		return MP_URL;
	}

	@PostConstruct
	public void init() {
		MP_HOST = SystemPropUtil.getProp("MP_IP");
		MP_PORT = SystemPropUtil.getProp("MP_PORT");
		MP_URL = "http://" + MP_HOST + ":" + MP_PORT;

		log.info("mp host=[{}], port=[{}]", MP_HOST, MP_PORT);
	}
}
