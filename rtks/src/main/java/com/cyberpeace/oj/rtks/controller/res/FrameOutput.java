package com.cyberpeace.oj.rtks.controller.res;

import com.cyberpeace.oj.rtks.dao.entity.Level;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class FrameOutput {

	private String resourceId;

	@ApiModelProperty(value = "框架名称")
	private String name;

	@ApiModelProperty(value = "框架描述")
	private String description;

	public FrameOutput(Level level) {
		this.resourceId = level.getResourceId();
		this.name = level.getName();
		this.description = level.getDescription();
	}

}
