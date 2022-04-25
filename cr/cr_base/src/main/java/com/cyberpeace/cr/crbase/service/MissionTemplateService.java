package com.cyberpeace.cr.crbase.service;

import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.dao.entity.MissionTemplate;

public interface MissionTemplateService extends IService<MissionTemplate> {

	/**
	 * 从 roleConfig, missionScheme 中解析出 id，再获取任务模板列表
	 * @param schemeJson
	 * @return
	 */
	List<MissionTemplate> batchGetByMissionSchemeJson(String schemeJson);

	/**
	 * 传入id列表，获取id：missionTemplate的字典
	 * @param templateIds
	 * @return
	 */
	Map<String, MissionTemplate> getMappingByIds(List<String> templateIds);

	/**
	 * 从 roleConfig, missionScheme 中解析出 id，再获取任务模板字典
	 * @param schemeJson
	 * @return
	 */
	Map<String, MissionTemplate> getMappingBySchemeJson(String schemeJson);

	/**
	 * 批量插入
	 * @param missionTemplateList
	 */
	void batchInsert(List<MissionTemplate> missionTemplateList);

	/**
	 * 批量删除
	 * @param missionTemplateIds
	 */
	void batchDelete(List<String> missionTemplateIds);

	/**
	 * 创建、更新BusinessTemplate时，批量更新任务的关联
	 * @param businessTemplateId
	 * @param missionTemplateIds
	 */
	void batchUpdateBusinessTemplateId(String businessTemplateId, List<String> missionTemplateIds);

	/**
	 * 从role_config json字符串中正则匹配出所有的任务模板id
	 * @param missionSchemeJson
	 * @return
	 */
	List<String> getIdsByMissionsSchemeJson(String missionSchemeJson);
}
