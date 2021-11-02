package com.cyberpeace.cr.teamtrain.model.role;

import java.util.List;

import lombok.Data;

@Data
public class RoleRspData {

	private String resourceId;

	private String name;

	private String missionsList;

	private List<NameElement> users;

}
