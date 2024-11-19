package com.cyberpeace.situation.model.ws;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * WS 攻击时间表
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class WsAttackSchedule implements Serializable {

	/**
	 * 名称
	 *
	 * @see String
	 */
	@ApiModelProperty("名称")
	private String name;

	/**
	 * 状态：0为阶段已运行，1为阶段正在运行，2为动效已运行，3为动效正在运行
	 *
	 * @see Integer
	 */
	@ApiModelProperty("状态：0为阶段已运行，1为阶段正在运行，2为动效已运行，3为动效正在运行")
	private Integer status;

	public WsAttackSchedule(String name, Integer status) {
		this.name = name;
		this.status = status;
	}

	public WsAttackSchedule() {}
}
