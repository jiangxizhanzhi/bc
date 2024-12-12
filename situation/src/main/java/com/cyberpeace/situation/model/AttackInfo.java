package com.cyberpeace.situation.model;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AttackInfo implements Serializable {

	private String resourceId;

	@ApiModelProperty("时间")
	private String time;

	@ApiModelProperty("源节点名称")
	private String sourceName;

	@ApiModelProperty("源节点ID")
	private String sourceId;

	@ApiModelProperty("目标节点名称")
	private String targetName;

	@ApiModelProperty("目标节点ID")
	private String targetId;

	@ApiModelProperty("攻击类型")
	private String attackType;

	@ApiModelProperty("漏洞名称")
	private String leakName;
}
