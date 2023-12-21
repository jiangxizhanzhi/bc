package com.cyberpeace.situation.controller.req;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.situation.model.MediaAnimation;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AttackMessageReq implements Serializable {

	@ApiModelProperty("资源id")
	private String resourceId;

	@ApiModelProperty("场景id")
	private String sceneId;

	@ApiModelProperty("动效类型")
	private String type;

	@ApiModelProperty("动效描述")
	private String description;

	@ApiModelProperty("源节点id")
	private String sourceId;

	@ApiModelProperty("目标节点id")
	private String targetId;

	@ApiModelProperty("间隔时间")
	private Integer intervalTime;

	@ApiModelProperty("持续时间")
	private Integer durationTime;

	@ApiModelProperty("是否聚焦")
	private Boolean lookAtTarget;

	@ApiModelProperty("线型")
	private String lineType;

	@ApiModelProperty("连接线路颜色-线的颜色")
	private String lineColor;

	@ApiModelProperty("过程线路颜色（新增）")
	private String lineProcessColor;

	@ApiModelProperty("线的宽度")
	private Integer lineWidth;

	@ApiModelProperty("媒体动效")
	private List<MediaAnimation> mediaAnimationList;

}
