package com.cyberpeace.cr.evaluation.controller.rsp;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalTaskIdRsp implements Serializable {

	@ApiModelProperty(value = "测评靶场模板id")
	private String taskId;

	public EvalTaskIdRsp(String taskId) {
		this.taskId = taskId;
	}

}
