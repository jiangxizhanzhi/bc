package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

public class EvalStepTemplateModifyReq extends EvalStepTemplateAddReq{

	@ApiModelProperty(value = "资源id", required = true)
	@NotBlank
	private String resourceId;
}
