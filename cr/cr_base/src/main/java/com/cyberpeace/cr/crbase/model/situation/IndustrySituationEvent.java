package com.cyberpeace.cr.crbase.model.situation;

import java.io.Serializable;

import com.cyberpeace.situation.model.LineStyle;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class IndustrySituationEvent implements Serializable {

	private Integer code;

	@ApiModelProperty("场景id")
	private String sceneId;

	@ApiModelProperty("事件时间")
	private Long time;

	@ApiModelProperty("持续时间（秒）")
	private Integer duration;

	@ApiModelProperty("事件名称")
	private String name;

	@ApiModelProperty("事件描述")
	private String description;

	@ApiModelProperty("事件类别")
	private String category;

	@ApiModelProperty("事件类型")
	private Integer type;

	@ApiModelProperty("线型")
	private LineStyle lineStyle;

	@ApiModelProperty("源节点id")
	private String sourceId;

	@ApiModelProperty("源队伍名称")
	private String sourceGroupName;

	@ApiModelProperty("目的节点id")
	private String targetId;

	@ApiModelProperty("目的队伍名称")
	private String targetGroupName;

	@ApiModelProperty("是否聚焦到目的节点")
	private Boolean lookAtTarget;

}
