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

	@ApiModelProperty("条件数量")
	private Integer conditionCount;

	@ApiModelProperty("告警名称")
	private String alarmName;

	@ApiModelProperty("告警描述")
	private String alarmDescription;

	@ApiModelProperty("告警事件级别")
	private Integer alarmLevel;

	@ApiModelProperty("自定义属性")
	private String alarmAttributes;

	@ApiModelProperty("标签")
	private List<String> labelList;

	@ApiModelProperty("创建时间")
	private String createTime;
}
