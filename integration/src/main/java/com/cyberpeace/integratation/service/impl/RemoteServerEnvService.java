package com.cyberpeace.integratation.service.impl;

import javax.annotation.PostConstruct;

import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.util.SystemPropUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RemoteServerEnvService {

	private String OFFICE_SERVER_URL;

	private String FILE_SERVER_URL;

	public String getOfficeHost() {
		return OFFICE_SERVER_URL;
	}

	public String getFileServerHost() {
		return FILE_SERVER_URL;
	}

	@PostConstruct
	public void init() {
		String OFFICE_SERVER_HOST = SystemPropUtil.getProp("OFFICE_SERVER_HOST");
		String OFFICE_SERVER_PORT = SystemPropUtil.getProp("OFFICE_SERVER_PORT");

		if (StringUtils.isNotBlank(OFFICE_SERVER_HOST) && OFFICE_SERVER_HOST.startsWith("http")) {
			OFFICE_SERVER_URL = OFFICE_SERVER_HOST + ":" + OFFICE_SERVER_PORT;
		} else {
			OFFICE_SERVER_URL = "http://" + OFFICE_SERVER_HOST + ":" + OFFICE_SERVER_PORT;
		}

		log.info("Office server url=[{}]", OFFICE_SERVER_URL);

		String FILE_SERVER_HOST = SystemPropUtil.getProp("FILE_SERVER_HOST");
		String FILE_SERVER_PORT = SystemPropUtil.getProp("FILE_SERVER_PORT");
		if (StringUtils.isNotBlank(FILE_SERVER_HOST) && FILE_SERVER_HOST.startsWith("http")) {
			FILE_SERVER_URL = FILE_SERVER_HOST + ":" + FILE_SERVER_PORT;
		} else {
			FILE_SERVER_URL = "http://" + FILE_SERVER_HOST + ":" + FILE_SERVER_PORT;
		}
		log.info("File server url=[{}]", FILE_SERVER_URL);
	}

}
