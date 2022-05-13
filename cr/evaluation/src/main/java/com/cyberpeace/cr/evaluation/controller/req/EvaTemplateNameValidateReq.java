package com.cyberpeace.cr.evaluation.controller.req;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvaTemplateNameValidateReq {

	@ApiModelProperty(value = "测评靶场模板名称", required = true)
	@NotNull
	private String templateName;

	@ApiModelProperty(value = "测评靶场模板Id")
	private String templateId;

}