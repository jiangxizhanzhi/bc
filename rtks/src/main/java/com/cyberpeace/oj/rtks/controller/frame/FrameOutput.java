package com.cyberpeace.oj.rtks.controller.frame;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FrameOutput {

	private String resourceId;

	@ApiModelProperty(value = "框架名称")
	private String name;

	@ApiModelProperty(value = "框架描述")
	private String description;

}
