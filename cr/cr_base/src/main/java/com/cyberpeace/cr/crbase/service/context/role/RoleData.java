package com.cyberpeace.cr.crbase.service.context.role;

import java.util.List;

import lombok.Data;

@Data
public class RoleData {

	private String resourceId;

	private String name;

	private String missionsList;

	private List<NameElement> users;

}
