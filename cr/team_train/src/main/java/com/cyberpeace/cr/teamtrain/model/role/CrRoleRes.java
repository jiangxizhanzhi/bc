package com.cyberpeace.cr.teamtrain.model.role;

import java.util.List;

import lombok.Data;
@Data
public class CrRoleRes {

	List<RoleResData> roles;

	Object missionMapping;

	Object showWriteupMapping;

}
