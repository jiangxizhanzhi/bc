package com.cyberpeace.bs.service.bo.variable;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Variable extends VariableSimple implements Serializable {

	@ApiModelProperty(value = "更新时间")
	private Date updateTime;
}
