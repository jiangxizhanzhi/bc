package com.cyberpeace.cr.crbase.model.situation;

import java.io.Serializable;

import com.cyberpeace.situation.model.LineStyle;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 工业态势事件
 *
 * @author SN
 * @date 2024/11/13
 */
@Data
public class IndustrySituationEvent implements Serializable {

	/**
	 * 代号
	 */
	private Integer code;

	/**
	 * 场景id
	 */
	@ApiModelProperty("场景id")
	private String sceneId;

	/**
	 * 时间
	 */
	@ApiModelProperty("事件时间")
	private Long time;

	/**
	 * 期间
	 */
	@ApiModelProperty("持续时间（秒）")
	private Integer duration;

	/**
	 * 名称
	 */
	@ApiModelProperty("事件名称")
	private String name;

	/**
	 * 描述
	 */
	@ApiModelProperty("事件描述")
	private String description;

	/**
	 * 类别
	 */
	@ApiModelProperty("事件类别")
	private String category;

	/**
	 * 类型
	 */
	@ApiModelProperty("事件类型")
	private Integer type;

	/**
	 * 线条样式
	 */
	@ApiModelProperty("线型")
	private LineStyle lineStyle;

	/**
	 * 源id
	 */
	@ApiModelProperty("源节点id")
	private String sourceId;

	/**
	 * 源群名称
	 */
	@ApiModelProperty("源队伍名称")
	private String sourceGroupName;

	/**
	 * 目标id
	 */
	@ApiModelProperty("目的节点id")
	private String targetId;

	/**
	 * 目标群名称
	 */
	@ApiModelProperty("目的队伍名称")
	private String targetGroupName;

	/**
	 * 查看目标
	 */
	@ApiModelProperty("是否聚焦到目的节点")
	private Boolean lookAtTarget;

}
