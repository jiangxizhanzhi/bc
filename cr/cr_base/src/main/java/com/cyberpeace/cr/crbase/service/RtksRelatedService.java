package com.cyberpeace.cr.crbase.service;

import java.util.Collection;
import java.util.List;

public interface RtksRelatedService {

	/**
	 * 删除任务模板时，删除技能和任务的关联
	 * @param missionTemplateIds 任务模板id列表
	 */
	void deleteSkillMissionRelation(List<String> missionTemplateIds);

	/**
	 * 删除场景模板时，删除attack关联
	 * @param businessTemplateIds 业务模板id列表
	 */
	void clearAttackRel(List<String> businessTemplateIds);

	/**
	 * 删除场景模板时，删除 engage关联
	 * @param businessTemplateIds 业务模板id 列表
	 */
	void clearEngageRel(List<String> businessTemplateIds);

	/**
	 * 去除单个场景模板和attack、engage框架的关联
	 * @param btId
	 */
	void clearAttackEngageRel(String btId);

	/**
	 * drm 导入自动关联attack、engage
	 * @param btId
	 * @param relatedTechs
	 */
	void createAttackEngageRel(String btId, Collection<String> relatedTechs);
}
