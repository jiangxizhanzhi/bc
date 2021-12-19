package com.cyberpeace.cr.crbase.service.context.role;

import java.util.List;

import com.cyberpeace.cr.crbase.service.context.MissionSchemeObject;

import lombok.Data;

@Data
public class RoleData<T> {

	private String resourceId;

	private String name;

	private List<MissionSchemeObject<T>> missionsList;

	private List<NameElement> users;

	private String userIds;

}
