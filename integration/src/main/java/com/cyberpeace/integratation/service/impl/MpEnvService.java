package com.cyberpeace.integratation.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.cyberpeace.common.util.SystemPropUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MpEnvService {

	private String MP_URL;

	public String getMpHost() {
		return MP_URL;
	}

	@PostConstruct
	public void init() {
		String MP_HOST = SystemPropUtil.getProp("MP_HOST");

		if (!MP_HOST.startsWith("http")) {
			log.error("[{}] not starts with http!", MP_HOST);
			throw new Error();
		}
		MP_URL = MP_HOST;

		log.info("mp url=[{}]", MP_URL);
	}
}
