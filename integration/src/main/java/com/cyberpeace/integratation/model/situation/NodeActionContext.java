package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class NodeActionContext implements Serializable {

	@ApiModelProperty(value = "攻击类型")
	private String attackType;

	@ApiModelProperty(value = "攻击子类型")
	private String attackSubType;

	@ApiModelProperty(value = "攻击描述")
	private String desc;

	@ApiModelProperty(value = "目标IP")
	private String targetIp;

	@ApiModelProperty(value = "时间")
	private String time;
}
