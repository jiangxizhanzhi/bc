package com.cyberpeace.cr.crbase.service.context.role;

import java.util.List;
import java.util.Map;

import com.cyberpeace.cr.crbase.service.context.MissionCompletion;

import lombok.Data;

@Data
public class CrRoleContext {

	List<RoleData> roles;

	Map<String, MissionCompletion> missionCompletionMapping;

	List<Map<String, ShowWriteup>> showWriteupMapping;

}
