package com.cyberpeace.situation.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AttackTypeCount implements Serializable {

	@ApiModelProperty("攻击类型")
	private String attackType;

	@ApiModelProperty("攻击次数")
	private Integer times;
}
