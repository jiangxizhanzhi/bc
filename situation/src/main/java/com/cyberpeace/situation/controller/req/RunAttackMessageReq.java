package com.cyberpeace.situation.controller.req;

import com.cyberpeace.situation.model.MediaAnimation;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class RunAttackMessageReq implements Serializable {

	@ApiModelProperty("场景id")
	private String sceneId;

	@ApiModelProperty("动效类型")
	private String type;

	@ApiModelProperty("动效描述")
	private String description;

	@ApiModelProperty("源节点id")
	private String sourceId;

	@ApiModelProperty("源节点名称")
	private String sourceName;

	@ApiModelProperty("目标节点id")
	private String targetId;

	@ApiModelProperty("目标节点名称")
	private String targetName;

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

	@ApiModelProperty("防御等级")
	private Integer defenseLevel;

	@ApiModelProperty("防御事件")
	private String defenseType;

	@ApiModelProperty("关联实景步骤")
	private Integer step;

	@ApiModelProperty("媒体动效")
	private List<MediaAnimation> mediaAnimationList;

	@ApiModelProperty("是否为实例场景")
	private Boolean isInstance;

	@ApiModelProperty("攻击或防御")
	private String attackOrDefense;

	@ApiModelProperty("防御节点")
	private String defenseTargetId;

	@ApiModelProperty("工具")
	private String tool;

	@ApiModelProperty("漏洞")
	private String leak;

}
