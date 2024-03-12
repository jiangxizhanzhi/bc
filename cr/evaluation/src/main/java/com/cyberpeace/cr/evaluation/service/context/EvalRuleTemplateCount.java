package com.cyberpeace.cr.evaluation.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EvalRuleTemplateCount implements Serializable {

	@ApiModelProperty("数量")
	private Integer count;

	@ApiModelProperty("规则id")
	private String ruleId;
}
