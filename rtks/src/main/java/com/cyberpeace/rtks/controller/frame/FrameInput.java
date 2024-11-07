package com.cyberpeace.rtks.controller.frame;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 框架输入
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class FrameInput {

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "框架名称")
	private String name;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "框架描述")
	private String description;
}
