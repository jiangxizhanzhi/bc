package com.cyberpeace.cr.crbase.service.context;

import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class DynamicData {

	@ApiModelProperty(value = "动作类型")
	private Integer actionType;

	@ApiModelProperty(value = "动作时间")
	private Date actionTime;

	@ApiModelProperty(value = "任务名")
	private String missionName;
}
