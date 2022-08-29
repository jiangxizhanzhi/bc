package com.cyberpeace.rtks.controller.frame;

import com.cyberpeace.rtks.controller.NotEmptyString;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FrameModifyInput extends FrameInput {

	@ApiModelProperty(value = "框架id")
	private String resourceId;

	@ApiModelProperty(value = "框架名称")
	@NotEmptyString
	private String name;

}
