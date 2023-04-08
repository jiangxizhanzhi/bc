package com.cyberpeace.cr.crbase.service.context;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AttackTypeScore implements Serializable {

	@ApiModelProperty("攻击方式")
	private String attackTypeName;

	@ApiModelProperty("得分")
	private Integer score;

	public AttackTypeScore(String attackTypeName, Integer score) {
		this.attackTypeName = attackTypeName;
		this.score = score;
	}
}
