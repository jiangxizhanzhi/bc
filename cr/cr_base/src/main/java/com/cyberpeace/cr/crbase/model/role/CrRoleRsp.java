package com.cyberpeace.cr.crbase.model.role;

import java.util.List;

import lombok.Data;

@Data
public class CrRoleRsp {

	List<RoleRspData> roles;

	Object missionMapping;

	Object showWriteupMapping;

}
