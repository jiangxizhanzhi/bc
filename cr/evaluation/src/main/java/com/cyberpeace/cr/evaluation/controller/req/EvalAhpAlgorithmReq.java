package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class EvalAhpAlgorithmReq {

	@ApiModelProperty(value = "算法Id", required = false)
	private String evalAlgorithmId;

	@ApiModelProperty(value = "层次分析法json数组", required = true)
	private String matrixData;
}
