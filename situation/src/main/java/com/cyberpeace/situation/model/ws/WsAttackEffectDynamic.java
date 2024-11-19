package com.cyberpeace.situation.model.ws;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.situation.model.AttackEffect;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * WS攻击动效动态
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class WsAttackEffectDynamic implements Serializable {

	/**
	 * 场景id
	 *
	 * @see String
	 */
	@ApiModelProperty("场景id")
	private String sceneId;

	/**
	 * 事件时间
	 *
	 * @see Long
	 */
	@ApiModelProperty("事件时间")
	private Long time;

	/**
	 * 事件名称
	 *
	 * @see String
	 */
	@ApiModelProperty("事件名称")
	private String name;

	/**
	 * 事件描述
	 *
	 * @see String
	 */
	@ApiModelProperty("事件描述")
	private String description;

	/**
	 * 事件类别
	 *
	 * @see String
	 */
	@ApiModelProperty("事件类别")
	private String category;

	/**
	 * 事件类型
	 *
	 * @see Integer
	 */
	@ApiModelProperty("事件类型")
	private Integer type;

	/**
	 * 攻击效果列表
	 *
	 * @see List<AttackEffect>
	 */
	@ApiModelProperty("攻击效果列表")
	private List<AttackEffect> effectList;

}
