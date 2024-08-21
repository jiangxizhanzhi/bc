package com.cyberpeace.cr.teamtrain.controller.req;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TeamTrainTemplateModifyReq extends TeamTrainTemplateAddReq {

	@ApiModelProperty(required = true, value = "模板编号")
	@NotNull
	private String resourceId;
}
