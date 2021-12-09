package com.cyberpeace.integratation.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.integratation.service.SystemConfigService;

@Service
public class SystemConfigServiceImpl implements SystemConfigService {

	private String SYS_CONFIG_URL;

	@PostConstruct
	public void init() {
		SYS_CONFIG_URL = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("sys.config.all");
	}

	@Autowired
	private MpEnvService mpEnvService;

	@Override
	public String getSystemConfig(String name) {

		JSONArray configArray = HttpClientUtil.rawGet(SYS_CONFIG_URL, JSONArray.class);
		for (Object obj : configArray) {
			JSONObject jsonObject = (JSONObject) obj;
			if (name.equals(jsonObject.getString("key_name"))) {
				return jsonObject.getString("value");
			}
		}
		return null;
	}
}
