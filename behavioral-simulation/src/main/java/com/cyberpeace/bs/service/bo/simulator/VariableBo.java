package com.cyberpeace.bs.service.bo.simulator;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VariableBo implements Serializable {

	@ApiModelProperty(value = "变量名")
	private String name;

	@ApiModelProperty(value = "变量值")
	private String value;
}
