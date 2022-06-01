package com.cyberpeace.cr.evaluation.controller.req;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvaTemplateModifyReq extends EvaTemplateAddReq {

	@ApiModelProperty(value = "测评靶场模板id", required = true)
	@NotEmpty
	private String resourceId;

}