package com.cyberpeace.bs.service.bo.behavior;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VariableListBo implements Serializable {

	@ApiModelProperty(value = "行为id")
	private String behaviorId;

	@ApiModelProperty(value = "变量列表")
	private List<VariableBo> variables;
}
