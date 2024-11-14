package com.cyberpeace.rtks.controller.frame;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LevelAddInput extends LevelInput {

	/**
	 * 层级名称
	 */
	@ApiModelProperty(value = "层级名称")
	@NotBlank
	private String name;

	/**
	 * 层级的父级层级/框架的id
	 */
	@ApiModelProperty(value = "层级的父级层级/框架的id")
	@NotBlank
	private String parent;
}
