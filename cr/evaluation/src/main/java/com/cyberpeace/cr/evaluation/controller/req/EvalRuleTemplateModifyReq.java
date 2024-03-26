package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
public class EvalRuleTemplateModifyReq extends EvalRuleTemplateAddReq {

	@ApiModelProperty(value = "规则id", required = true)
	private String ruleId;
}
