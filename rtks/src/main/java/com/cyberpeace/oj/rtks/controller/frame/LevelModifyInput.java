package com.cyberpeace.oj.rtks.controller.frame;

import com.cyberpeace.oj.rtks.controller.NotEmptyString;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LevelModifyInput extends LevelInput {

	private String resourceId;

	@ApiModelProperty(value = "层级名称")
	@NotEmptyString
	private String name;

	@ApiModelProperty(value = "层级的父级层级/框架的id")
	@NotEmptyString
	private String parent;

}
