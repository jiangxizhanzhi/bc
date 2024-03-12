package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;


@Data
public class EvalRuleTemplateAddReq implements Serializable {

	@ApiModelProperty(value = "云场景模板id", required = true)
	private String cloudTemplateId;

	@ApiModelProperty(value = "规则名称", required = true)
	private String ruleName;

	@ApiModelProperty("规则描述")
	private String description;
}
