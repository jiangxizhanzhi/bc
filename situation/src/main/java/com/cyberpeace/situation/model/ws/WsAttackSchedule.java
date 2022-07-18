package com.cyberpeace.situation.model.ws;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WsAttackSchedule implements Serializable {

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("状态：0为阶段已运行，1为阶段正在运行，2为动效已运行，3为动效正在运行")
	private Integer status;

	public WsAttackSchedule(String name, Integer status) {
		this.name = name;
		this.status = status;
	}

	public WsAttackSchedule() {}
}
