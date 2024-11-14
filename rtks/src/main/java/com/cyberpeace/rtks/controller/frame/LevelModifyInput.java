package com.cyberpeace.rtks.controller.frame;

import com.cyberpeace.rtks.controller.NotEmptyString;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LevelModifyInput extends LevelInput {

	private String resourceId;

	/**
	 * 层级名称
	 */
	@ApiModelProperty(value = "层级名称")
	@NotEmptyString
	private String name;

	/**
	 * 层级的父级层级/框架的id
	 */
	@ApiModelProperty(value = "层级的父级层级/框架的id")
	@NotEmptyString
	private String parent;

}
