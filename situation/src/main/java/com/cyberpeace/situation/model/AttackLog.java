package com.cyberpeace.situation.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AttackLog implements Serializable {

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
}
