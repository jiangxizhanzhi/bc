package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateEvalTemTemplateReq extends EvalTemTemplateReq {

	@ApiModelProperty("资源id")
	private String resourceId;
}
