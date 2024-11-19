package com.cyberpeace.rtks.controller.frame;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 框架层级新增输入
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see LevelInput
 * @since 1.0.0
 */
@Data
public class LevelAddInput extends LevelInput {

	/**
	 * 层级名称
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "层级名称")
	@NotBlank
	private String name;

	/**
	 * 层级的父级层级/框架的id
	 *
	 * @see String
	 */
	@ApiModelProperty(value = "层级的父级层级/框架的id")
	@NotBlank
	private String parent;
}
