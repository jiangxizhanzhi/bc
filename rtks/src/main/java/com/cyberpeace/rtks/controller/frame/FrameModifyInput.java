package com.cyberpeace.rtks.controller.frame;

import com.cyberpeace.rtks.controller.NotEmptyString;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FrameModifyInput extends FrameInput {

	/**
	 * 框架id
	 */
	@ApiModelProperty(value = "框架id")
	private String resourceId;

	/**
	 * 框架名称
	 */
	@ApiModelProperty(value = "框架名称")
	@NotEmptyString
	private String name;

}
