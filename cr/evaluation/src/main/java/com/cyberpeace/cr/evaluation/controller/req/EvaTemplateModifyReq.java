package com.cyberpeace.cr.evaluation.controller.req;

import javax.validation.constraints.NotEmpty;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvaTemplateModifyReq extends EvaTemplateAddReq {

	@ApiModelProperty("测试靶场模板id")
	@NotEmpty
	private String resourceId;

}