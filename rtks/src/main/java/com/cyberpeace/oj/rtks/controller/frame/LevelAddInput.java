package com.cyberpeace.oj.rtks.controller.frame;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LevelAddInput extends LevelInput {

	@ApiModelProperty(value = "层级名称")
	@NotBlank
	private String name;

	@ApiModelProperty(value = "层级的父级层级/框架的id")
	@NotBlank
	private String parent;
}
