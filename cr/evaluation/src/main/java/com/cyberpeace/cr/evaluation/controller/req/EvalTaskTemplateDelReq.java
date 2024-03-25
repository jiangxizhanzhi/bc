package com.cyberpeace.cr.evaluation.controller.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class EvalTaskTemplateDelReq implements Serializable {

	@ApiModelProperty("规则id")
	private String ruleId;

	@ApiModelProperty("步骤id列表")
	private List<String> stepIds;
}
