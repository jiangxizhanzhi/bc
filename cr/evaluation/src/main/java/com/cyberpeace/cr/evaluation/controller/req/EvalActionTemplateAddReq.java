package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EvalActionTemplateAddReq implements Serializable {

	@ApiModelProperty(value = "云模板id", required = true)
	private String cloudTemplateId;

	@ApiModelProperty(value = "规则id", required = true)
	private String ruleId;

	@ApiModelProperty(value = "动作类型", required = true)
	private Integer actionType;

	@ApiModelProperty(value = "名称", required = true)
	private String name;

	@ApiModelProperty("级别")
	private Integer level;

	@ApiModelProperty("攻击类型")
	private Integer attackType;

	@ApiModelProperty("攻击源")
	private String sourceId;

	@ApiModelProperty("攻击目标")
	private String targetId;

	@ApiModelProperty("攻击结果")
	private Integer result;
}
