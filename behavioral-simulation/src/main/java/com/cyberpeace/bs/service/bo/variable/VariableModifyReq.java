package com.cyberpeace.bs.service.bo.variable;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VariableModifyReq implements Serializable {

	@ApiModelProperty(value = "行为id")
	private String behaviorId;

	@ApiModelProperty(value = "行为变量")
	private List<VariableSimple> variables;
}
