package com.cyberpeace.oj.rtks.controller.frame;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FrameAddInput extends FrameInput {

	@ApiModelProperty(value = "框架名称")
	@NotBlank
	private String name;

}
