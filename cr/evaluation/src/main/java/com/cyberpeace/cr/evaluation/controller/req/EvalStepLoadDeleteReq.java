package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EvalStepLoadDeleteReq implements Serializable {

	@ApiModelProperty("载荷id")
	private String loadId;

	@ApiModelProperty("步骤id")
	private String stepId;
}
