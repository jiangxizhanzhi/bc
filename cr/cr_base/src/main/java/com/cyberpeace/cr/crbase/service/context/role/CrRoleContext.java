package com.cyberpeace.cr.crbase.service.context.role;

import java.util.List;
import java.util.Map;

import com.cyberpeace.cr.crbase.service.context.MissionCompletion;

import lombok.Data;

@Data
public class CrRoleContext<T> {

	private List<RoleData<T>> roles;

	private Map<String, MissionCompletion> missionMapping;

	private Map<String, ShowWriteup> showWriteupMapping;

}
