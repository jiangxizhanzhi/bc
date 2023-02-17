package com.cyberpeace.oj.course.controller.res;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel("单个用户角色")

public class Teacher {

	@ApiModelProperty("用户id")
	private String resourceId;

	@ApiModelProperty("用户名")
	private String name;


}
