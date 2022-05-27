package com.cyberpeace.cs.controller.req.task;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TaskStopReq implements Serializable {

	@ApiModelProperty(value = "任务ID")
	private String taskId;

	@ApiModelProperty(value = "任务结果（上报给共性平台的数据）")
	private String result;
}
