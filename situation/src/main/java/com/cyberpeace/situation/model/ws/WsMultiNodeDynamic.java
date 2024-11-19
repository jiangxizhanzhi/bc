package com.cyberpeace.situation.model.ws;

import java.io.Serializable;

import com.cyberpeace.situation.model.LineStyle;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * WS 多节点动态
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class WsMultiNodeDynamic implements Serializable {

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
	 * 持续时间（秒）
	 *
	 * @see Integer
	 */
	@ApiModelProperty("持续时间（秒）")
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
	 * 事件类别
	 *
	 * @see String
	 */
	@ApiModelProperty("事件类别")
	private String category;

	/**
	 * {@linkplain com.cyberpeace.situation.constant.ws.ActionType code}
	 *
	 * @see Integer
	 */
	@ApiModelProperty("事件类型")
	private Integer type;

	/**
	 * 线型
	 *
	 * @see LineStyle
	 */
	@ApiModelProperty("线型")
	private LineStyle lineStyle;

	/**
	 * 源节点id
	 *
	 * @see String
	 */
	@ApiModelProperty("源节点id")
	private String sourceId;

	/**
	 * 源队伍名称
	 *
	 * @see String
	 */
	@ApiModelProperty("源队伍名称")
	private String sourceGroupName;

	/**
	 * 目的节点id
	 *
	 * @see String
	 */
	@ApiModelProperty("目的节点id")
	private String targetId;

	/**
	 * 目的队伍名称
	 *
	 * @see String
	 */
	@ApiModelProperty("目的队伍名称")
	private String targetGroupName;

	/**
	 * 是否聚焦到目的节点
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否聚焦到目的节点")
	private Boolean lookAtTarget;

}
