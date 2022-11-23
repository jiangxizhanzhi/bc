package com.cyberpeace.bs.service.bo.variable;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VariableSimple implements Serializable {

	@ApiModelProperty(value = "变量id")
	private String id;

	@ApiModelProperty(value = "变量名称")
	private String name;

	@ApiModelProperty(value = "变量值")
	private String value;
}
