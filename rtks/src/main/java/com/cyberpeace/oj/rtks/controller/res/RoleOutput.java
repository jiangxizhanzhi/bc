package com.cyberpeace.oj.rtks.controller.res;

import java.util.ArrayList;
import java.util.List;

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

	static public RoleOutput fromRole(Role role) {
		return new RoleOutput(role);
	}

	static public List<RoleOutput> listFromRoles(List<Role> roles) {
		List<RoleOutput> roleOutputs = new ArrayList<>();
		for (Role role : roles) {
			roleOutputs.add(fromRole(role));
		}
		return roleOutputs;
	}

}
