package com.cyberpeace.cr.teamtrain.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class DailyTrainTemplateModifyReq extends DailyTrainTemplateAddReq {

	@ApiModelProperty(required = true, value = "模板编号")
	@NotNull
	private String resourceId;
}
