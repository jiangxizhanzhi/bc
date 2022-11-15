package com.cyberpeace.bs.service.bo.behavior;

import java.io.Serializable;
import java.util.Date;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class VariableBo implements Serializable {

	@ApiModelProperty(value = "变量id")
	private String id;

	@ApiModelProperty(value = "变量类型")
	private Integer type;

	@ApiModelProperty(value = "变量名称")
	private String name;

	@ApiModelProperty(value = "变量值")
	private String value;

	@ApiModelProperty(value = "更新时间")
	private Date updateTime;
}
