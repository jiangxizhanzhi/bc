package com.cyberpeace.cr.teamtrain.controller.rsp;

import java.util.List;

import lombok.Data;

@Data
public class TeamRoomRoleRes {

	private String resourceId;

	private String name;

	private String trainingTeam;

	private String description;

	private Integer roleGroup;

	private String nodes;

	private List<String> users;

	private List<UserData> usersData;

	private String missionScheme;

}
