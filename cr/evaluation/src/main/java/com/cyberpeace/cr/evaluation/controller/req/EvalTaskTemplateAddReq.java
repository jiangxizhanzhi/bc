package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
public class EvalTaskTemplateAddReq implements Serializable {

	@ApiModelProperty("规则id")
	private String ruleId;

	@ApiModelProperty("步骤id")
	private String stepId;

}
