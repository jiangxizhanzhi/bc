package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EvalAlarmReq implements Serializable {

	@ApiModelProperty("资源id")
	private String resourceId;

	@ApiModelProperty(required = true, value = "告警名称")
	private String alarmName;

	@ApiModelProperty(required = true, value = "描述")
	private String description;

	@ApiModelProperty(required = true, value = "告警事件级别")
	private Integer level;

	@ApiModelProperty(required = true, value = "告警级别名")
	private String alarmLevelName;

}
