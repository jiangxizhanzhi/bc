package com.cyberpeace.bs.service.bo.variable;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VariableBo extends VariableSimple implements Serializable {

	@ApiModelProperty(value = "变量类型")
	private Integer type;

	@ApiModelProperty(value = "更新时间")
	private Date updateTime;
}
