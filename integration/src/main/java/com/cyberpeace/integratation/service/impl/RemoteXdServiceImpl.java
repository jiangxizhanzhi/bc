package com.cyberpeace.integratation.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.integratation.service.RemoteXdService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class RemoteXdServiceImpl implements RemoteXdService {

	private String XD_URL;

	@PostConstruct
	public void init() {
		String XD_HOST = SystemPropUtil.getProp("XD_HOST");
		String XD_PORT = SystemPropUtil.getProp("XD_PORT");
		String XD_PATH = SystemPropUtil.getProp("XD_PATH");
		XD_URL = "http://" + XD_HOST + ":" + XD_PORT + "/" + XD_PATH;
	}

	@Override
	public Boolean getXdStatus(String hdnm, String xdid) {

		// 如果获取不到hdnm，则使用空字符串
		hdnm = hdnm == null ? "" : hdnm;

		// 构造请求地址
		String url = XD_URL + "?xdid=" + xdid + "&hdnm=" + hdnm;

		// 打印请求地址
		log.info("getXdStatus url: {}", url);

		// 执行请求
		Boolean rsp = false;
		try {
			String result = HttpClientUtil.rawGet(url, String.class);
			log.info("getXdStatus result: {}", result);
			rsp = JsonUtil.toJsonObject(result, JSONObject.class).getBoolean("status");
		} catch (Exception e) {
			log.error("getXdStatus error:", e);
		}

		return rsp;

	}

}
