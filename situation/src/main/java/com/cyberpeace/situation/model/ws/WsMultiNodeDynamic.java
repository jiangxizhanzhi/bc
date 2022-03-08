package com.cyberpeace.situation.model.ws;

import java.io.Serializable;

import com.cyberpeace.situation.model.ElementInfo;
import com.cyberpeace.situation.model.LineStyle;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WsMultiNodeDynamic implements Serializable {

	@ApiModelProperty("场景id")
	private String sceneId;

	@ApiModelProperty("事件时间")
	private String time;

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

	@ApiModelProperty("源节点信息（更新部分）")
	private ElementInfo sourceElementInfo;

	@ApiModelProperty("目的节点id")
	private String targetId;

	@ApiModelProperty("目的队伍名称")
	private String targetGroupName;

	@ApiModelProperty("目的节点信息（更新部分）")
	private ElementInfo targetElementInfo;

	@ApiModelProperty("是否聚焦到目的节点")
	private Boolean lookAtTarget;

}
