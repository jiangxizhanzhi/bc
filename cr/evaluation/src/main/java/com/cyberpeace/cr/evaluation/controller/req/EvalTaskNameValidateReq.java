package com.cyberpeace.cr.evaluation.controller.req;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalTaskNameValidateReq {

	@ApiModelProperty(value = "测评靶场任务名称", required = true)
	@NotNull
	private String taskName;

	@ApiModelProperty(value = "测评靶场模板Id", required = true)
	private String templateId;

	@ApiModelProperty(value = "测评靶场任务Id")
	private String taskId;

}