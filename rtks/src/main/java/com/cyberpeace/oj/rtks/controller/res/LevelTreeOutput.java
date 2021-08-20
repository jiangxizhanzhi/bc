package com.cyberpeace.oj.rtks.controller.res;

import java.util.List;

import com.cyberpeace.oj.rtks.dao.entity.Level;

import lombok.Data;

@Data
public class LevelTreeOutput {

	private String resourceId;

	private String name;

	private String description;

	private List<LevelTreeOutput> children;

	public LevelTreeOutput(Level level) {
		this.resourceId = level.getResourceId();
		this.name = level.getName();
		this.description = level.getDescription();
	}

}
