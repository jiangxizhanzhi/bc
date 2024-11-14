package com.cyberpeace.rtks.controller.frame;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FrameAddInput extends FrameInput {

	/**
	 * 框架名称
	 */
	@ApiModelProperty(value = "框架名称")
	@NotBlank
	private String name;

}
