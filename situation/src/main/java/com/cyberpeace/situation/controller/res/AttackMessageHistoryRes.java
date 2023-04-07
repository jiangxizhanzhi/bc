package com.cyberpeace.situation.controller.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AttackMessageHistoryRes implements Serializable {

	@ApiModelProperty("时间")
	private String time;

	@ApiModelProperty("动效描述")
	private String description;

	@ApiModelProperty("攻击还是防御")
	private String attackOrDefense;

	public AttackMessageHistoryRes() {
	}

	public AttackMessageHistoryRes(String time, String description, String attackOrDefense) {
		this.time = time;
		this.description = description;
		this.attackOrDefense = attackOrDefense;
	}
}
