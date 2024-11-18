package com.cyberpeace.cr.crbase.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.cyberpeace.common.model.NameCount;

/**
 * RTKS相关Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
public interface RtksRelatedService {

	/**
	 * 删除任务模板时，删除技能和任务的关联
	 *
	 * @param missionTemplateIds 任务模板id列表
	 * @throws
	 * @see List<String>
	 */
	void deleteSkillMissionRelation(List<String> missionTemplateIds);

	/**
	 * 删除场景模板时，删除attack关联
	 *
	 * @param businessTemplateIds 业务模板id列表
	 * @throws
	 * @see List<String>
	 */
	void clearAttackRel(List<String> businessTemplateIds);

	/**
	 * 删除场景模板时，删除 engage关联
	 *
	 * @param businessTemplateIds 业务模板id 列表
	 * @throws
	 * @see List<String>
	 */
	void clearEngageRel(List<String> businessTemplateIds);

	/**
	 * 去除单个场景模板和attack、engage框架的关联
	 *
	 * @param btId BT系列id
	 * @throws
	 * @see String
	 */
	void clearAttackEngageRel(String btId);

	/**
	 * drm 导入自动关联attack、engage
	 *
	 * @param btId
	 * @param relatedTechs 相关技术
	 * @throws
	 * @see String
	 * @see Collection<String>
	 */
	void createAttackEngageRel(String btId, Collection<String> relatedTechs);

	/**
	 * 统计训练模板关联各attack类别下的技术数量
	 * <p>
	 * <p>
	 * 用于训练列表页面
	 *
	 * @param businessTemplateIds btId列表
	 * @return map
	 * @throws
	 * @see List<String>
	 * @see Map<String, List<NameCount>>
	 */
	Map<String, List<NameCount>> countAttackRel(List<String> businessTemplateIds);

	/**
	 * 统计训练模板关联各engage类别下的技术数量
	 * <p>
	 * <p>
	 * 用于训练列表页面
	 *
	 * @param businessTemplateIds btId列表
	 * @return map
	 * @throws
	 * @see List<String>
	 * @see Map<String, List<NameCount>>
	 */
	Map<String, List<NameCount>> countEngageRel(List<String> businessTemplateIds);

	/**
	 * 统计训练模板关联各attack ics类别下的技术数量
	 * <p>
	 * <p>
	 * 用于训练列表页面
	 *
	 * @param businessTemplateIds btId列表
	 * @return map
	 * @throws
	 * @see List<String>
	 * @see Map<String, List<NameCount>>
	 */
	Map<String, List<NameCount>> countIcsRel(List<String> businessTemplateIds);
}
