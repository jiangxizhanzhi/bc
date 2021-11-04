package com.cyberpeace.cr.crbase.service.context.role;

import java.util.List;

import lombok.Data;

@Data
public class CrRoleContext {

	List<RoleData> roles;

	Object missionMapping;

	Object showWriteupMapping;

}
