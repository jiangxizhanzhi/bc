package com.cyberpeace.situation.service.context;

import java.io.Serializable;

import org.eclipse.jetty.util.StringUtil;

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
		this.config = StringUtil.isEmpty(config) ? null
				: JsonUtil.toJsonObject(config, JSONObject.class);
	}

}
