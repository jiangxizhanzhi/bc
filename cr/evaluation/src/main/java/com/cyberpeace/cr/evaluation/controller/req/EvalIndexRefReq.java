package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Data
public class EvalIndexRefReq {

	@ApiModelProperty(value = "评估体系Id", required = true)
	@NotEmpty
	private String resourceId;

	@ApiModelProperty(value = "描述", required = false)
	@NotNull
	private Integer type;

}