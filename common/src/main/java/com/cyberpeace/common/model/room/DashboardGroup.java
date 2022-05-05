package com.cyberpeace.common.model.room;

import java.util.List;

import lombok.Data;

@Data
public class DashboardGroup {

	private String groupname;

	private String role;

	private String id;

	private String avatar;

	private List<String> members;

	public DashboardGroup(String role) {
		this.role = role;
		this.avatar = "";
		this.groupname = role;
		this.id = role;
	}
}
