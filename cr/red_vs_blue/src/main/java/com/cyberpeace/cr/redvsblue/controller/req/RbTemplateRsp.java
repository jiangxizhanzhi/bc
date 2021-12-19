package com.cyberpeace.cr.redvsblue.controller.req;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.redvsblue.controller.rsp.BaseInfoRsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RbTemplateRsp {

	private BaseInfoRsp baseInfo;

	private String resourceId;

	@ApiModelProperty(required = true)
	@NotNull
	private List<JSONObject> redRoles;

	@ApiModelProperty(required = true)
	@NotNull
	private List<JSONObject> blueRoles;
}
