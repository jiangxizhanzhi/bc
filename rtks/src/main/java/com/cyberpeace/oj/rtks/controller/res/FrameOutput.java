package com.cyberpeace.oj.rtks.controller.res;

import com.cyberpeace.oj.rtks.dao.entity.Level;

import lombok.Data;

@Data
public class FrameOutput {

	private String resourceId;

	private String name;

	private String description;

	public FrameOutput(Level level) {
		this.resourceId = level.getResourceId();
		this.name = level.getName();
		this.description = level.getDescription();
	}

}
