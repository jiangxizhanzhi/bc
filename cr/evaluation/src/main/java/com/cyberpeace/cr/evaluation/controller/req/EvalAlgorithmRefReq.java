package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class EvalAlgorithmRefReq {

	@ApiModelProperty(value = "算法Id", required = true)
	private String resourceId;

	@ApiModelProperty(value = "代码", required = true)
	@NotNull
	private String code;


}
