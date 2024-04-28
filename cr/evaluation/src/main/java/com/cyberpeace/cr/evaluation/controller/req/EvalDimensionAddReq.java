package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EvalDimensionAddReq implements Serializable {

	@ApiModelProperty("标注id")
	private String dimensionId;

	@ApiModelProperty(value = "事件名称", required = true)
	private String name;

	@ApiModelProperty(value = "类型")
	private Integer type;

	@ApiModelProperty(value = "事件类型", required = true)
	private Integer eventType;

	@ApiModelProperty(value = "规则参数", required = true)
	private String ruleParameter;

}
