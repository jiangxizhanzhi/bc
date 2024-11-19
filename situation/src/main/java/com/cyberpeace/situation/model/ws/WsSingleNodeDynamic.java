package com.cyberpeace.situation.model.ws;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * WS 单节点动态
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class WsSingleNodeDynamic implements Serializable {

	/**
	 * 场景id
	 *
	 * @see String
	 */
	@ApiModelProperty("场景id")
	private String sceneId;

	/**
	 * 事件时间（毫秒时间戳）
	 *
	 * @see Long
	 */
	@ApiModelProperty("事件时间（毫秒时间戳）")
	private Long time;

	/**
	 * 持续时间
	 *
	 * @see Integer
	 */
	@ApiModelProperty("持续时间")
	private Integer duration;

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
	 * 事件类型 11-区域高亮 12-节点聚焦
	 *
	 * @see Integer
	 */
	@ApiModelProperty("事件类型 11-区域高亮 12-节点聚焦")
	private Integer type;

	/**
	 * 目标id
	 *
	 * @see String
	 */
	@ApiModelProperty("目标id")
	private String targetId;
}
