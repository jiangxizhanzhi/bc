package com.cyberpeace.oj.rtks.controller.res;

import com.cyberpeace.oj.rtks.dao.entity.Level;

import lombok.Data;

@Data
public class LevelOutput {

	private String resourceId;

	private String name;

	private String description;

	private String parent;

	public LevelOutput(Level level) {
		this.resourceId = level.getResourceId();
		this.name = level.getName();
		this.description = level.getDescription();
		this.parent = level.getParent();
	}

	public static LevelOutput fromLevel(Level level) {
		return new LevelOutput(level);
	}

}
