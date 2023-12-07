package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateEvalMissionSchemeTemplateReq extends EvalMissionSchemeTemplateReq{

	@ApiModelProperty(name = "资源id", required = true)
	private String resourceId;

}
