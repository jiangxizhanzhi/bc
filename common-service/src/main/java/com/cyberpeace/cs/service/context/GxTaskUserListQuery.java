package com.cyberpeace.cs.service.context;

import java.io.Serializable;

import com.cyberpeace.common.model.PageReqBase;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class GxTaskUserListQuery extends PageReqBase implements Serializable {

	@ApiModelProperty(name = "task_id", value = "任务id")
	private String taskId;

	public void setTask_id(String taskId) {
		this.taskId = taskId;
	}

}
