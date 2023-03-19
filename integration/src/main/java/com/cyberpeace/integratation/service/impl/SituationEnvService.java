package com.cyberpeace.integratation.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.cyberpeace.common.util.SystemPropUtil;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class SituationEnvService {

	private String YH_HOST;

	private String YH_PORT;

	private String YH_URL;

	private String TS_HOST;

	private String TS_PORT;

	private String TS_URL;

	private String TS_GL_HOST;

	private String TS_GL_PORT;

	private String TS_GL_URL;

	private String PG_HOST;

	private String PG_PORT;

	private String PG_URL;

	private String PG_PAGE_HOST;

	private String PG_PAGE_PORT;

	private String PG_PAGE_URL;

	private String XD_HOST;

	private String XD_PORT;

	private String XD_URL;

	private String YW_HOST;

	private String YW_PORT;

	private String YW_URL;

	public String getYhHost() {
		return YH_URL;
	}

	public String getTsHost() {
		return TS_URL;
	}

	public String getTsGlHost() {
		return TS_GL_URL;
	}

	public String getPgHost() {
		return PG_URL;
	}

	public String getPgPageHost() {
		return PG_PAGE_URL;
	}

	public String getXdHost() {
		return XD_URL;
	}

	public String getYwHost() {
		return YW_URL;
	}

	@PostConstruct
	public void init() {

		// 获取用户服务配置
		YH_URL = initEnv("YH", "用户", YH_HOST, YH_PORT);

		// 获取态势感知服务配置
		TS_URL = initEnv("TS", "态势感知", TS_HOST, TS_PORT);

		// 获取态势管理服务配置
		TS_GL_URL = initEnv("TS_GL", "态势管理", TS_GL_HOST, TS_GL_PORT);

		// 获取评估服务配置
		PG_URL = initEnv("PG", "评估", PG_HOST, PG_PORT);

		// 获取评估页面服务配置
		PG_PAGE_URL = initEnv("PG_PAGE", "评估页面", PG_PAGE_HOST, PG_PAGE_PORT);

		// 获取想定服务配置
		XD_URL = initEnv("XD", "想定", XD_HOST, XD_PORT);

		// 获取运维服务配置
		YW_URL = initEnv("YW", "运维", YW_HOST, YW_PORT);
	}

	private String initEnv(String serverCode, String serverName, String host, String port) {
		host = SystemPropUtil.getProp( "OUTER_HOST");
		port = SystemPropUtil.getProp("OUTER_PORT");
		String msg = String.format("服务配置：host=[%s], port=[%s]", host, port);
		log.info(serverName + msg);
		return "http://" + host + ":" + port;
	}
}
