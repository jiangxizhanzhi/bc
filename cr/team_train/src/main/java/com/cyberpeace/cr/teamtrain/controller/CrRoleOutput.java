package com.cyberpeace.cr.teamtrain.controller;

import java.util.List;

import lombok.Data;
@Data
public class CrRoleOutput {

	List<RoleOutputData> roles;

	Object missionMapping;

	Object showWriteupMapping;

}
