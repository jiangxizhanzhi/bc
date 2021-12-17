package com.cyberpeace.cr.redvsblue.service.context;

import java.util.List;
import java.util.Map;

import com.cyberpeace.cr.crbase.service.context.MissionCompletion;
import com.cyberpeace.cr.crbase.service.context.role.ShowWriteup;

import lombok.Data;

@Data
public class CrRoleContext<T> {

	private List<GroupData> groups;

	private Map<String, MissionCompletion> missionMapping;

	private Map<String, ShowWriteup> showWriteupMapping;

	private List<String> dynamic;
}
