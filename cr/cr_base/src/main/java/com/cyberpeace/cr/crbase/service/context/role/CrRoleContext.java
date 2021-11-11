package com.cyberpeace.cr.crbase.service.context.role;

import java.util.List;
import java.util.Map;

import com.cyberpeace.cr.crbase.service.context.MissionMapping;

import lombok.Data;

@Data
public class CrRoleContext {

	List<RoleData> roles;

	MissionMapping missionMapping;

	List<Map<String, ShowWriteup>> showWriteupMapping;

}
