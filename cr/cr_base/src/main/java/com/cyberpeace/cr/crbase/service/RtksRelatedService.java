package com.cyberpeace.cr.crbase.service;

import java.util.List;

public interface RtksRelatedService {

	/**
	 * 删除任务模板时，删除技能和任务的关联
	 * @param missionTemplateIds
	 */
	void deleteSkillMissionRelation(List<String> missionTemplateIds);
}
