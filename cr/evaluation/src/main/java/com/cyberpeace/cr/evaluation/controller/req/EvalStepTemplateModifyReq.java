package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class EvalStepTemplateModifyReq extends EvalStepTemplateAddReq{

	@ApiModelProperty(value = "步骤id", required = true)
	@NotBlank
	private String stepId;
}
