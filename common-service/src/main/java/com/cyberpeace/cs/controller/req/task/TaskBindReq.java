package com.cyberpeace.cs.controller.req.task;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TaskBindReq implements Serializable {

	@ApiModelProperty(value = "任务ID")
	private String taskId;

	@ApiModelProperty(value = "事件类型 1-考试 2-课程")
	private Integer eventType;

	@ApiModelProperty(value = "事件ID")
	private String eventId;
}
