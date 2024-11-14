package com.cyberpeace.situation.model.ws;

import java.io.Serializable;

import com.cyberpeace.situation.model.LineStyle;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WsMultiNodeDynamic implements Serializable {

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
	 * 持续时间（秒）
	 */
	@ApiModelProperty("持续时间（秒）")
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
	 * 事件类别
	 */
	@ApiModelProperty("事件类别")
	private String category;

	/**
	{@linkplain com.cyberpeace.situation.constant.ws.ActionType code}
	 */
	@ApiModelProperty("事件类型")
	private Integer type;

	/**
	 * 线型
	 */
	@ApiModelProperty("线型")
	private LineStyle lineStyle;

	/**
	 * 源节点id
	 */
	@ApiModelProperty("源节点id")
	private String sourceId;

	/**
	 * 源队伍名称
	 */
	@ApiModelProperty("源队伍名称")
	private String sourceGroupName;

	/**
	 * 目的节点id
	 */
	@ApiModelProperty("目的节点id")
	private String targetId;

	/**
	 * 目的队伍名称
	 */
	@ApiModelProperty("目的队伍名称")
	private String targetGroupName;

	/**
	 * 是否聚焦到目的节点
	 */
	@ApiModelProperty("是否聚焦到目的节点")
	private Boolean lookAtTarget;

}
