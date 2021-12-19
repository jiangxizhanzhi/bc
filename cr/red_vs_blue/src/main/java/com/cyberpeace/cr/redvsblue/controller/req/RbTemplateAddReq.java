package com.cyberpeace.cr.redvsblue.controller.req;

import java.util.List;

import javax.validation.constraints.NotNull;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.cr.crbase.service.impl.bo.BasicTemplate;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RbTemplateAddReq extends BasicTemplate {

	@ApiModelProperty(required = true)
	@NotNull
	private List<JSONObject> redRoles;

	@ApiModelProperty(required = true)
	@NotNull
	private List<JSONObject> blueRoles;

}