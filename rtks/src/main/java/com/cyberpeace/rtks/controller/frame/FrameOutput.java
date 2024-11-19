package com.cyberpeace.rtks.controller.frame;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 框架输出
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Data
public class FrameOutput {

	/**
	 * 框架id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "框架id")
	private String resourceId;

	/**
	 * 框架名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "框架名称")
	private String name;

	/**
	 * 框架描述
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "框架描述")
	private String description;

}
