package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;


@Data
public class EvalRuleTemplateAddReq implements Serializable {

	@ApiModelProperty(value = "云场景模板id", required = true)
	private String cloudTemplateId;

	@ApiModelProperty(value = "规则名称", required = true)
	private String ruleName;

	@ApiModelProperty("规则描述")
	private String description;

	@ApiModelProperty(value = "状态", required = true)
	private Integer status;

	@ApiModelProperty(value = "标签", required = true)
	private List<String> labels;

	@ApiModelProperty(value = "等级", required = true)
	private Integer grade;

	@ApiModelProperty(value = "布尔关系", required = true)
	private Integer booleanRelation;

	@ApiModelProperty(value = "标注")
	private List<EvalDimensionAddReq> dimensionList;

	@ApiModelProperty(value = "主机")
	private List<EvalHostTemplateAddReq> hostList;

	@ApiModelProperty(value = "任务")
	private List<String> stepIds;

	@ApiModelProperty("触发警告")
	private List<EvalAlarmReq> alarms;

	@ApiModelProperty("态势展示")
	private List<EvalSituationReq> situations;
}
