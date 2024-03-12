package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EvalDimensionAddReq implements Serializable {

	@ApiModelProperty("规则id")
	private String ruleId;

	@ApiModelProperty("云模板id")
	private String cloudTemplateId;

	@ApiModelProperty("事件名称")
	private String name;

	@ApiModelProperty("事件类型")
	private Integer eventType;

	@ApiModelProperty("操作")
	private Integer operate;

	@ApiModelProperty("对象")
	private String object;

	@ApiModelProperty("取值")
	private String value;

}
