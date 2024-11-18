package com.cyberpeace.cr.crbase.service;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.crbase.dao.entity.MissionTemplate;
import com.cyberpeace.cr.crbase.service.context.mission.MissionTemplateRes;

/**
 * 任务模板Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see IService
 * @since 1.0.0
 */
public interface MissionTemplateService extends IService<MissionTemplate> {

	/**
	 * 从 roleConfig, missionScheme 中解析出 id，再获取任务模板列表
	 *
	 * @param schemeJson json字符串
	 * @return 任务模板列表
	 * @throws
	 * @see String
	 * @see List<MissionTemplate>
	 */
	List<MissionTemplate> batchGetByMissionSchemeJson(String schemeJson);

	/**
	 * 传入id列表，获取id：missionTemplate的字典
	 *
	 * @param templateIds 任务模板id列表
	 * @return 任务模板集合
	 * @throws
	 * @see List<String>
	 * @see Map<String, MissionTemplate>
	 */
	Map<String, MissionTemplate> getMappingByIds(List<String> templateIds);

	/**
	 * 从 roleConfig, missionScheme 中解析出 id，再获取任务模板字典
	 *
	 * @param schemeJson json字符串
	 * @return 任务模板集合
	 * @throws
	 * @see String
	 * @see Map<String, MissionTemplate>
	 */
	Map<String, MissionTemplate> getMappingBySchemeJson(String schemeJson);

	/**
	 * 批量插入任务模板
	 *
	 * @param missionTemplateList 任务模板列表
	 * @throws
	 * @see List<MissionTemplate>
	 */
	void batchInsert(List<MissionTemplate> missionTemplateList);

	/**
	 * 批量删除任务模板
	 *
	 * @param missionTemplateIds 任务模板id列表
	 * @throws
	 * @see List<String>
	 */
	void batchDelete(List<String> missionTemplateIds);

	/**
	 * 创建、更新BusinessTemplate时，批量更新任务的关联
	 *
	 * @param businessTemplateId 业务模板id
	 * @param missionTemplateIds 任务模板id列表
	 * @throws
	 * @see String
	 * @see List<String>
	 */
	void batchUpdateBusinessTemplateId(String businessTemplateId, List<String> missionTemplateIds);

	/**
	 * 从role_config json字符串中正则匹配出所有的任务模板id
	 *
	 * @param missionSchemeJson json字符串
	 * @return 任务模板id列表
	 * @throws
	 * @see String
	 * @see List<String>
	 */
	List<String> getIdsByMissionsSchemeJson(String missionSchemeJson);

	/**
	 * 用于rtks，连同场景名称返回任务信息
	 *
	 * @param missionTemplateIds 任务模板id列表
	 * @return 任务信息集合
	 * @throws
	 * @see List<String>
	 * @see Map<String, MissionTemplateRes>
	 */
	Map<String, MissionTemplateRes> mapWithBusiness(List<String> missionTemplateIds);

	/**
	 * 查出businessTemplate关联的所有任务
	 *
	 * @param businessTemplateId
	 * @return
	 * @throws
	 * @see String
	 * @see List<String>
	 */
	List<String> getIdsByBusinessTemplateId(String businessTemplateId);

	/**
	 * 从 roleConfig, missionScheme 中解析出 id，再获取任务模板字典
	 *
	 * @param schemeJsonList json字符串列表
	 * @return 任务模板集合
	 * @throws
	 * @see List<String>
	 * @see Map<String, MissionTemplate>
	 */
	Map<String, MissionTemplate> getMappingBySchemeJsonList(List<String> schemeJsonList);

	/**
	 * 通过ids获取批量
	 *
	 * @param missionTemplateIds 任务模板id列表
	 * @return {@code List<MissionTemplate>}
	 * @throws
	 * @see Collection<String>
	 * @see List<MissionTemplate>
	 */
	List<MissionTemplate> getBatchByIds(Collection<String> missionTemplateIds);
}
