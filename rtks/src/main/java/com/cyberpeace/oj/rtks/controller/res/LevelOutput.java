package com.cyberpeace.oj.rtks.controller.res;

import com.cyberpeace.oj.rtks.dao.entity.Level;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LevelOutput {

	private String resourceId;

	@ApiModelProperty(value = "层级名称")
	private String name;

	@ApiModelProperty(value = "层级描述")
	private String description;

	@ApiModelProperty(value = "层级的父级层级/框架的id")
	private String parent;

	public LevelOutput(Level level) {
		this.resourceId = level.getResourceId();
		this.name = level.getName();
		this.description = level.getDescription();
		this.parent = level.getParent();
	}

}
