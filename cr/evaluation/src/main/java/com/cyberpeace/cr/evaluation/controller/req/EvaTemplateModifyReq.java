package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvaTemplateModifyReq extends EvaTemplateAddReq {

	@ApiModelProperty(value = "任务id", required = false)
	private String taskId;

}