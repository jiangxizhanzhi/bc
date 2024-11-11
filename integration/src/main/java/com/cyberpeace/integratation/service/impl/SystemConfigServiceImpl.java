package com.cyberpeace.integratation.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.HttpClientUtil;
import com.cyberpeace.integratation.service.SystemConfigService;

/**
 * 系统配置Service实现
 *
 * @author SN
 * @date 2024/11/11
 */
@Service
public class SystemConfigServiceImpl implements SystemConfigService {

	/**
	 * 系统配置 URL
	 */
	private String SYS_CONFIG_URL;

	/**
	 * 初始化
	 */
	@PostConstruct
	public void init() {
		SYS_CONFIG_URL = mpEnvService.getMpHost() + ConfigUtil.getContextProperty("sys.config.all");
	}

	/**
	 * 中台环境Service
	 */
	@Autowired
	private MpEnvService mpEnvService;

	/**
	 * 获取系统配置
	 *
	 * @param name 名称
	 * @return {@code String }
	 */
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
