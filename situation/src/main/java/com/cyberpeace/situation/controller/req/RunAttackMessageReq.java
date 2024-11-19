package com.cyberpeace.situation.controller.req;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.situation.model.MediaAnimation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 跑攻击消息请求体
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class RunAttackMessageReq implements Serializable {

	/**
	 * 场景id
	 *
	 * @see String
	 */
	@ApiModelProperty("场景id")
	private String sceneId;

	/**
	 * 动效类型
	 *
	 * @see String
	 */
	@ApiModelProperty("动效类型")
	private String type;

	/**
	 * 动效描述
	 *
	 * @see String
	 */
	@ApiModelProperty("动效描述")
	private String description;

	/**
	 * 源节点id
	 *
	 * @see String
	 */
	@ApiModelProperty("源节点id")
	private String sourceId;

	/**
	 * 源节点名称
	 *
	 * @see String
	 */
	@ApiModelProperty("源节点名称")
	private String sourceName;

	/**
	 * 目标节点id
	 *
	 * @see String
	 */
	@ApiModelProperty("目标节点id")
	private String targetId;

	/**
	 * 目标节点名称
	 *
	 * @see String
	 */
	@ApiModelProperty("目标节点名称")
	private String targetName;

	/**
	 * 间隔时间
	 *
	 * @see Integer
	 */
	@ApiModelProperty("间隔时间")
	private Integer intervalTime;

	/**
	 * 持续时间
	 *
	 * @see Integer
	 */
	@ApiModelProperty("持续时间")
	private Integer durationTime;

	/**
	 * 是否聚焦
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否聚焦")
	private Boolean lookAtTarget;

	/**
	 * 线型
	 *
	 * @see String
	 */
	@ApiModelProperty("线型")
	private String lineType;

	/**
	 * 线的颜色
	 *
	 * @see String
	 */
	@ApiModelProperty("线的颜色")
	private String lineColor;

	/**
	 * 线的宽度
	 *
	 * @see Integer
	 */
	@ApiModelProperty("线的宽度")
	private Integer lineWidth;

	/**
	 * 媒体动效
	 *
	 * @see List<MediaAnimation>
	 */
	@ApiModelProperty("媒体动效")
	private List<MediaAnimation> mediaAnimationList;

	/**
	 * 是否为实例场景
	 *
	 * @see Boolean
	 */
	@ApiModelProperty("是否为实例场景")
	private Boolean isInstance;

}
