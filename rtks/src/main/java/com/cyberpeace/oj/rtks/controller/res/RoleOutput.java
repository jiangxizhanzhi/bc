package com.cyberpeace.oj.rtks.controller.res;

import com.cyberpeace.oj.rtks.dao.entity.Role;

import lombok.Data;

@Data
public class RoleOutput {

	private String resourceId;

	private String name;

	private String description;

	private String levelId;

	private int category;

	public RoleOutput(Role role) {
		this.resourceId = role.getResourceId();
		this.name = role.getName();
		this.description = role.getDescription();
		this.levelId = role.getLevelId();
		this.category = role.getCategory();
	}

}
