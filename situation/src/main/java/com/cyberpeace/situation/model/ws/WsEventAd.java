package com.cyberpeace.situation.model.ws;

import java.io.Serializable;
import java.util.List;

import com.cyberpeace.integratation.model.situation.AdEventContext;
import com.cyberpeace.situation.model.EventInfo;
import com.cyberpeace.situation.model.LineStyle;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class WsEventAd implements Serializable {

	@ApiModelProperty("事件结果")
	private String result = "success";

	@ApiModelProperty("源节点ID")
	private String sourceId;

	@ApiModelProperty("源节点父节点ID列表")
	private List<String> sourceParentIds;

	@ApiModelProperty("源平台ID")
	private String sourcePlatformId;

	@ApiModelProperty("目的节点ID")
	private String targetId;

	@ApiModelProperty("目的节点父节点ID列表")
	private List<String> targetParentIds;

	@ApiModelProperty("目的平台ID")
	private String targetPlatformId;

	@ApiModelProperty("动效类别 1-内置 2-映射")
	private Integer dynamicType;

	@ApiModelProperty("源节点动效类型（内置）")
	private Integer sourceActionType;

	@ApiModelProperty("目的节点动效类型（内置）")
	private Integer targetActionType;

	@ApiModelProperty("源节点动效路径")
	private String sourceNodeDynamicDir;

	@ApiModelProperty("目的节点动效路径")
	private String targetNodeDynamicDir;

	@ApiModelProperty("线型")
	private LineStyle lineStyle;

	@ApiModelProperty("是否聚焦到目的节点")
	private Boolean lookAtTarget;

	@ApiModelProperty("持续时间（秒）")
	private Integer duration = 5;

	@ApiModelProperty("事件信息")
	private EventInfo eventInfo;

	public void load(AdEventContext context) {
		this.sourceId = context.getSourceId();
		this.targetId = context.getTargetId();
		EventInfo info = new EventInfo();
		info.load(context);
		this.eventInfo = info;
	}

}
