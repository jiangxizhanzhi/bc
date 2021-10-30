package com.cyberpeace.cr.teamtrain.model.role;

import java.util.List;

import lombok.Data;

@Data
public class RoleUsers {

	private String roleName;

	private Integer roleGroup;

	private List<String> users;

}
