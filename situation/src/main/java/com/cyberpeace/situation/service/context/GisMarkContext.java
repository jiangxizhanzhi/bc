package com.cyberpeace.situation.service.context;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import com.cyberpeace.common.baseinterface.Update;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.util.JsonUtil;

import cn.hutool.json.JSONObject;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GisMarkContext implements Serializable {

	@ApiModelProperty(value = "标绘ID")
	@NotBlank(groups = { Update.class }, message = ErrorCode.RESOURCE_NOT_FOUND)
	private String markId;

	@ApiModelProperty(value = "大屏ID", required = true)
	private String dashboardId;

	@ApiModelProperty(value = "标绘配置", required = true)
	private JSONObject markConfig;

	public void setResourceId(String resourceId) {
		this.markId = resourceId;
	}

	public void setConfig(String config) {
		this.markConfig = JsonUtil.toJsonObject(config, JSONObject.class);
	}
}
