package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;


@Data
public class EvalRuleTemplateModifyReq extends EvalRuleTemplateAddReq {

	@ApiModelProperty(value = "规则id", required = true)
	private String ruleId;

	@ApiModelProperty(value = "状态", required = true)
	private Integer status;

	@ApiModelProperty(value = "标签", required = true)
	private List<String> labels;

	@ApiModelProperty(value = "等级", required = true)
	private Integer grade;

	@ApiModelProperty(value = "布尔关系", required = true)
	private Integer booleanRelation;
}
