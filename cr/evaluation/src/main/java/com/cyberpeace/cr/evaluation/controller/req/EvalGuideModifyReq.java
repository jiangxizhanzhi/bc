package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class EvalGuideModifyReq extends EvalGuideAddReq {

	@ApiModelProperty(value = "导调id", required = true)
	@NotBlank
	private String guideId;
}
