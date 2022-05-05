package com.cyberpeace.cr.evaluation.controller.req;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalTaskStopReq {

	@ApiModelProperty(value = "靶场测试模板Id", required = true)
	@NotNull
	private String templateId;

	@ApiModelProperty(value = "任务Id", required = true)
	@NotNull
	private String taskId;

	@ApiModelProperty(value = "任务名称", required = false)
	@NotNull
	private String taskName;

}