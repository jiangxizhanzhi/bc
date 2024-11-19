package com.cyberpeace.rtks.controller.frame;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 层级输出
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Data
public class LevelOutput {

	/**
	 * 层级id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "层级id")
	private String resourceId;

	/**
	 * 层级名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "层级名称")
	private String name;

	/**
	 * 层级描述
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "层级描述")
	private String description;

	/**
	 * 层级的父级层级/框架的id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "层级的父级层级/框架的id")
	private String parent;

	/**
	 * 框架id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "框架id")
	private String frame;
}
