package com.cyberpeace.situation.model;

import com.cyberpeace.situation.controller.req.DynamicSchemeReq;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class DynamicSchemeProcess implements Serializable {

	@ApiModelProperty("动效方案数据")
	private DynamicSchemeReq dynamicSchemeReq;

	@ApiModelProperty("阶段名称")
	private String stageName;

	@ApiModelProperty("动效描述")
	private String attackMessageDescription;

}
