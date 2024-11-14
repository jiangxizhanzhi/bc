package com.cyberpeace.situation.model.ws;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.situation.model.AttackEffect;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WsAttackEffectDynamic implements Serializable {

	/**
	 * 场景id
	 */
	@ApiModelProperty("场景id")
	private String sceneId;

	/**
	 * 事件时间
	 */
	@ApiModelProperty("事件时间")
	private Long time;

	/**
	 * 事件名称
	 */
	@ApiModelProperty("事件名称")
	private String name;

	/**
	 * 事件描述
	 */
	@ApiModelProperty("事件描述")
	private String description;

	/**
	 * 事件类别
	 */
	@ApiModelProperty("事件类别")
	private String category;

	/**
	 * 事件类型
	 */
	@ApiModelProperty("事件类型")
	private Integer type;

	/**
	 * 攻击效果列表
	 */
	@ApiModelProperty("攻击效果列表")
	private List<AttackEffect> effectList;

}
