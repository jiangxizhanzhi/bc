package com.cyberpeace.rtks.controller.frame;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LevelOutput {

	@ApiModelProperty(value = "层级id")
	private String resourceId;

	@ApiModelProperty(value = "层级名称")
	private String name;

	@ApiModelProperty(value = "层级描述")
	private String description;

	@ApiModelProperty(value = "层级的父级层级/框架的id")
	private String parent;

	@ApiModelProperty(value = "框架id")
	private String frame;
}
