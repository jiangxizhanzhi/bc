package com.cyberpeace.cr.teamtrain.controller;

import java.util.List;

import lombok.Data;
@Data
public class RoleOutputData {

	private String resourceId;

	private String name;

	private String missionsList;

	private List<NameElement> users;

}
