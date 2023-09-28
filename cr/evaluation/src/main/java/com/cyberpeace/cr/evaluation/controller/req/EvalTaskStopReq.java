package com.cyberpeace.cr.evaluation.controller.req;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalTaskStopReq {

	@ApiModelProperty(value = "靶场测评模板Id", required = true)
	@NotNull
	private String templateId;

	@ApiModelProperty(value = "测评任务Id", required = true)
	private String taskId;

	@ApiModelProperty(value = "测评任务名称", required = false)
	private String taskName;

}