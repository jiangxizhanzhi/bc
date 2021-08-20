package com.cyberpeace.oj.rtks.controller.req;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LevelAddInput implements Serializable {

	@ApiModelProperty(value = "层级名称")
	@NotBlank
	private String name;

	@ApiModelProperty(value = "层级描述")
	private String description;

	@NotBlank
	@ApiModelProperty(value = "层级的父级层级/框架的id")
	private String parent;

}
