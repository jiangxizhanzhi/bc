package com.cyberpeace.cr.evaluation.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EvalDimensionContext implements Serializable {

	@ApiModelProperty("标注id")
	private String dimensionId;

	@ApiModelProperty("类型")
	private String type;

	@ApiModelProperty("事件名称")
	private String name;

	@ApiModelProperty("事件类型")
	private String eventType;

	@ApiModelProperty("规则参数")
	private String ruleParameter;
}
