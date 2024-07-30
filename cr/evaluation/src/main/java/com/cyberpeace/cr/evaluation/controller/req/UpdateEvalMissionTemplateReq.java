package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class UpdateEvalMissionTemplateReq extends EvalMissionTemplateReq {

	@ApiModelProperty("资源id")
	private String resourceId;

	@ApiModelProperty("模板id")
	private String taskId;

	@ApiModelProperty("测评靶场模板id")
	private String templateId;

}
