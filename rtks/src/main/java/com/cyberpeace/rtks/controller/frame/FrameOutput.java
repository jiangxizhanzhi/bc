package com.cyberpeace.rtks.controller.frame;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FrameOutput {

	/**
	 * 框架id
	 */
	@ApiModelProperty(value = "框架id")
	private String resourceId;

	/**
	 * 框架名称
	 */
	@ApiModelProperty(value = "框架名称")
	private String name;

	/**
	 * 框架描述
	 */
	@ApiModelProperty(value = "框架描述")
	private String description;

}
