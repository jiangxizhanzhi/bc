package com.cyberpeace.situation.model.ws;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WsSingleNodeDynamic implements Serializable {

	/**
	 * 场景id
	 */
	@ApiModelProperty("场景id")
	private String sceneId;

	/**
	 * 事件时间（毫秒时间戳）
	 */
	@ApiModelProperty("事件时间（毫秒时间戳）")
	private Long time;

	/**
	 * 持续时间
	 */
	@ApiModelProperty("持续时间")
	private Integer duration;

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
	 * 事件类型 11-区域高亮 12-节点聚焦
	 */
	@ApiModelProperty("事件类型 11-区域高亮 12-节点聚焦")
	private Integer type;

	/**
	 * 目标id
	 */
	@ApiModelProperty("目标id")
	private String targetId;
}
