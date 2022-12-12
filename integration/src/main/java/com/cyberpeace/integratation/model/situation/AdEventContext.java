package com.cyberpeace.integratation.model.situation;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class AdEventContext implements Serializable {

	@ApiModelProperty("事件ID")
	private String eventId;

	@ApiModelProperty("事件时间")
	private String eventTime;

	@ApiModelProperty("事件名称")
	private String eventName;

	@ApiModelProperty("告警级别")
	private String eventSeverity;

	@ApiModelProperty("事件类型")
	private String eventType;

	@ApiModelProperty("事件子类型")
	private String eventSubType;

	@ApiModelProperty("事件描述")
	private String description;

	@ApiModelProperty("源节点ID")
	private String sourceId;

	@ApiModelProperty("源节点IP")
	private String sourceIp;

	@ApiModelProperty("目标节点ID")
	private String targetId;

	@ApiModelProperty("目标节点IP")
	private String targetIp;

}
