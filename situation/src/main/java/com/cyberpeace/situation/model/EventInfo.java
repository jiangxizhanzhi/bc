package com.cyberpeace.situation.model;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EventInfo implements Serializable {

	@ApiModelProperty("事件ID")
	private String eventId;

	@ApiModelProperty("事件名称")
	private String eventName;

	@ApiModelProperty("事件时间")
	private Date eventTime;

	@ApiModelProperty("事件类型")
	private String eventType;

	@ApiModelProperty("事件子类型")
	private String eventSubType;

	@ApiModelProperty("事件描述")
	private String description;

	@ApiModelProperty("告警级别")
	private String eventSeverity;

	@ApiModelProperty("源节点IP")
	private String sourceIp;

	@ApiModelProperty("源节点阵营")
	private String sourceGroup;

	@ApiModelProperty("目的节点IP")
	private String targetIp;

	@ApiModelProperty("目的节点阵营")
	private String targetGroup;

}
