package com.cyberpeace.rtks.controller.frame;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Level 输出
 *
 * @author SN
 * @date 2024/11/07
 */
@Data
public class LevelOutput {

	/**
	 * 资源id
	 */
	@ApiModelProperty(value = "层级id")
	private String resourceId;

	/**
	 * 名称
	 */
	@ApiModelProperty(value = "层级名称")
	private String name;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "层级描述")
	private String description;

	/**
	 * 父母
	 */
	@ApiModelProperty(value = "层级的父级层级/框架的id")
	private String parent;

	/**
	 * 框架
	 */
	@ApiModelProperty(value = "框架id")
	private String frame;
}
