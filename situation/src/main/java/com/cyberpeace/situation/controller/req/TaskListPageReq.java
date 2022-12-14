package com.cyberpeace.situation.controller.req;

import java.io.Serializable;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TaskListPageReq extends PageReqBase implements Serializable {

	@ApiModelProperty(name = "sub_range_id", value = "分靶场ID")
	private String subRangeId;

	@ApiModelProperty(name = "responsible_range_id", value = "责任靶场ID")
	private String responsibleRangeId;

	@ApiModelProperty(name = "task_id", value = "任务ID")
	private String taskId;

	@ApiModelProperty(name = "task_type", value = "任务类型")
	private String taskType;

	@ApiModelProperty(name = "task_status", value = "任务状态")
	private String taskStatus;

	public void setSub_range_id(String subRangeId) {
		this.subRangeId = subRangeId;
	}

	public void setResponsible_range_id(String responsibleRangeId) {
		this.responsibleRangeId = responsibleRangeId;
	}

	public void setTask_id(String taskId) {
		this.taskId = taskId;
	}

	public void setTask_type(String taskType) {
		this.taskType = taskType;
	}

	public void setTask_status(String taskStatus) {
		this.taskStatus = taskStatus;
	}

}
