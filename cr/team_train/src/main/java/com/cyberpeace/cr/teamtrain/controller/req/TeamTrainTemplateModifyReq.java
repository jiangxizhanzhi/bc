package com.cyberpeace.cr.teamtrain.controller.req;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeamTrainTemplateModifyReq extends TeamTrainTemplateReq {

	@ApiModelProperty(required = true)
	@NotNull
	private String resourceId;
}
