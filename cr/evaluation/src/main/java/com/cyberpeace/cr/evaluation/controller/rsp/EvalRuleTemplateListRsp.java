package com.cyberpeace.cr.evaluation.controller.rsp;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EvalRuleTemplateListRsp implements Serializable {

	@ApiModelProperty("规则id")
	private String ruleId;

	@ApiModelProperty("规则名称")
	private String ruleName;

	@ApiModelProperty("规则描述")
	private String description;

	@ApiModelProperty("状态")
	private String status;

	@ApiModelProperty("标注数量")
	private Integer dimensionCount;

	@ApiModelProperty("标签")
	private List<String> labelList;

	@ApiModelProperty("等级")
	private String grade;

	@ApiModelProperty("布尔关系")
	private String booleanRelation;

	@ApiModelProperty("动作数量")
	private Integer actionCount;

	@ApiModelProperty("创建时间")
	private String createTime;
}
