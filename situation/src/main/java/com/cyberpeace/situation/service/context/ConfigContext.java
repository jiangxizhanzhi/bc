package com.cyberpeace.situation.service.context;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.util.JsonUtil;

import lombok.Data;

@Data
public class ConfigContext implements Serializable {

	private String resourceId;

	private String name;

	private JSONObject config;

	private String thumbnail;

	public void setConfig(String config) {
		this.config = StringUtils.isEmpty(config) ? null
				: JsonUtil.toJsonObject(config, JSONObject.class);
	}

}
