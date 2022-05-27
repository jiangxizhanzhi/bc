package com.cyberpeace.cs.service.context;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class TaskUserContext implements Serializable {

	@ApiModelProperty(value = "登陆名")
	private String userName;

	@ApiModelProperty(value = "中文名")
	private String name;

	@ApiModelProperty(value = "部门")
	private String orgName;

}