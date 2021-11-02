package com.cyberpeace.cr.teamtrain.model.role;

import java.util.List;

import lombok.Data;

@Data
public class CrRoleRsp {

	List<RoleRspData> roles;

	Object missionMapping;

	Object showWriteupMapping;

}
