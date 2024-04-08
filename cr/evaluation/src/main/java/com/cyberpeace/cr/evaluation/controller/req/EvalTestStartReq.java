package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class EvalTestStartReq implements Serializable {

	@ApiModelProperty(value = "试验id", required = true)
	@NotNull
	private String testId;
}
