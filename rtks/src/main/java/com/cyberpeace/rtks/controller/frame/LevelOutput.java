package com.cyberpeace.rtks.controller.frame;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LevelOutput {

	/**
	 * 层级id
	 */
	@ApiModelProperty(value = "层级id")
	private String resourceId;

	/**
	 * 层级名称
	 */
	@ApiModelProperty(value = "层级名称")
	private String name;

	/**
	 * 层级描述
	 */
	@ApiModelProperty(value = "层级描述")
	private String description;

	/**
	 * 层级的父级层级/框架的id
	 */
	@ApiModelProperty(value = "层级的父级层级/框架的id")
	private String parent;

	/**
	 * 框架id
	 */
	@ApiModelProperty(value = "框架id")
	private String frame;
}
