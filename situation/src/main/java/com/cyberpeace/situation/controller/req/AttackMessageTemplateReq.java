package com.cyberpeace.situation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class AttackMessageTemplateReq implements Serializable {

	@ApiModelProperty("资源id")
	private String resourceId;

	@ApiModelProperty("动效类型")
	private String type;

	@ApiModelProperty("动效描述")
	private String description;

	@ApiModelProperty("间隔时间")
	private Integer intervalTime;

	@ApiModelProperty("持续时间")
	private Integer durationTime;

	@ApiModelProperty("是否聚焦")
	private Boolean lookAtTarget;

	@ApiModelProperty("线型")
	private String lineType;

	@ApiModelProperty("线的颜色")
	private String lineColor;

	@ApiModelProperty("线的宽度")
	private Integer lineWidth;

	@ApiModelProperty("防御类型")
	private String defenseType;
}
