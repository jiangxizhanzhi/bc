package com.cyberpeace.situation.controller.req;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.situation.model.MediaAnimation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class RunAttackMessageReq implements Serializable {

	/**
	 * 场景id
	 */
	@ApiModelProperty("场景id")
	private String sceneId;

	/**
	 * 动效类型
	 */
	@ApiModelProperty("动效类型")
	private String type;

	/**
	 * 动效描述
	 */
	@ApiModelProperty("动效描述")
	private String description;

	/**
	 * 源节点id
	 */
	@ApiModelProperty("源节点id")
	private String sourceId;

	/**
	 * 源节点名称
	 */
	@ApiModelProperty("源节点名称")
	private String sourceName;

	/**
	 * 目标节点id
	 */
	@ApiModelProperty("目标节点id")
	private String targetId;

	/**
	 * 目标节点名称
	 */
	@ApiModelProperty("目标节点名称")
	private String targetName;

	/**
	 * 间隔时间
	 */
	@ApiModelProperty("间隔时间")
	private Integer intervalTime;

	/**
	 * 持续时间
	 */
	@ApiModelProperty("持续时间")
	private Integer durationTime;

	/**
	 * 是否聚焦
	 */
	@ApiModelProperty("是否聚焦")
	private Boolean lookAtTarget;

	/**
	 * 线型
	 */
	@ApiModelProperty("线型")
	private String lineType;

	/**
	 * 线的颜色
	 */
	@ApiModelProperty("线的颜色")
	private String lineColor;

	/**
	 * 线的宽度
	 */
	@ApiModelProperty("线的宽度")
	private Integer lineWidth;

	/**
	 * 媒体动效
	 */
	@ApiModelProperty("媒体动效")
	private List<MediaAnimation> mediaAnimationList;

	/**
	 * 是否为实例场景
	 */
	@ApiModelProperty("是否为实例场景")
	private Boolean isInstance;

}
