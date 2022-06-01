package com.cyberpeace.cr.evaluation.controller.req;

import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalTaskStartReq {

	@ApiModelProperty(value = "靶场测评模板Id", required = true)
	@NotNull
	private String templateId;

}