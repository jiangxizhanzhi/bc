package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.util.JsonUtil;

import lombok.Data;

/**
 * @Author xuxz
 * @Date 27/2/2023 下午2:15
 */
@Data
public class SituationTopoReq implements Serializable {

	@NotBlank(groups = { Update.class })
	private String resourceId;

	private String name;

	private String config;

	private String thumbnail;

	public void setConfig(JSONObject config) {
		this.config = config == null ? null : JsonUtil.toJsonString(config);
	}
}
