package com.cyberpeace.rtks.controller.frame;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 框架输入
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Serializable
 * @since 1.0.0
 */
@Data
public class LevelInput implements Serializable {

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

}
