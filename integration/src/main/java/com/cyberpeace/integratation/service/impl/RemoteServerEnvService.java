package com.cyberpeace.integratation.service.impl;

import com.cyberpeace.common.util.SystemPropUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

/**
 * 远程服务器环境Service
 *
 * @author SN
 * @date 2024/11/11
 */
@Slf4j
@Service
public class RemoteServerEnvService {

	/**
	 * Office Server 主机
	 */
	private String OFFICE_SERVER_HOST;

	/**
	 * Office 服务器端口
	 */
	private String OFFICE_SERVER_PORT;

	/**
	 * Office 服务器 URL
	 */
	private String OFFICE_SERVER_URL;

	/**
	 * 文件服务器主机
	 */
	private String FILE_SERVER_HOST;

	/**
	 * 文件服务器端口
	 */
	private String FILE_SERVER_PORT;

	/**
	 * 文件服务器 URL
	 */
	private String FILE_SERVER_URL;

	/**
	 * 获取办公室主机
	 *
	 * @return {@code String }
	 */
	public String getOfficeHost() {
		return OFFICE_SERVER_URL;
	}

	/**
	 * 获取文件服务器主机
	 *
	 * @return {@code String }
	 */
	public String getFileServerHost() {
		return FILE_SERVER_URL;
	}

	/**
	 * 初始化
	 */
	@PostConstruct
	public void init() {
		OFFICE_SERVER_HOST = SystemPropUtil.getProp("OFFICE_SERVER_HOST");
		OFFICE_SERVER_PORT = SystemPropUtil.getProp("OFFICE_SERVER_PORT");

		if (StringUtils.isNotBlank(OFFICE_SERVER_HOST) && OFFICE_SERVER_HOST.startsWith("http")) {
			OFFICE_SERVER_URL = OFFICE_SERVER_HOST + ":" + OFFICE_SERVER_PORT;
		} else {
			OFFICE_SERVER_URL = "http://" + OFFICE_SERVER_HOST + ":" + OFFICE_SERVER_PORT;
		}

		log.info("Office server url=[{}]", OFFICE_SERVER_URL);

		FILE_SERVER_HOST = SystemPropUtil.getProp("FILE_SERVER_HOST");
		FILE_SERVER_PORT = SystemPropUtil.getProp("FILE_SERVER_PORT");
		if (StringUtils.isNotBlank(FILE_SERVER_HOST) && FILE_SERVER_HOST.startsWith("http")) {
			FILE_SERVER_URL = FILE_SERVER_HOST + ":" + FILE_SERVER_PORT;
		} else {
			FILE_SERVER_URL = "http://" + FILE_SERVER_HOST + ":" + FILE_SERVER_PORT;
		}
		log.info("File server url=[{}]", FILE_SERVER_URL);
	}

}
