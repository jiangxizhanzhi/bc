package com.cyberpeace.cr.evaluation.controller.req;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalTaskStepReq {

	@ApiModelProperty(value = "测评步骤Id", required = true)
	@NotNull
	private String stepId;

}