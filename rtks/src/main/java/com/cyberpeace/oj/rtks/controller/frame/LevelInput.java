package com.cyberpeace.oj.rtks.controller.frame;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LevelInput implements Serializable {

	@ApiModelProperty(value = "层级名称")
	private String name;

	@ApiModelProperty(value = "层级描述")
	private String description;

	@ApiModelProperty(value = "层级的父级层级/框架的id")
	private String parent;

}
