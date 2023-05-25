package com.cyberpeace.rtks.service;

import java.util.List;
import java.util.Map;

import com.cyberpeace.rtks.service.bo.EngageDetail;
import com.cyberpeace.rtks.service.bo.career.EngageBackBo;
import com.cyberpeace.rtks.service.bo.career.EngageCategoryBo;
import com.cyberpeace.rtks.service.bo.career.EngageFrontBo;
import com.cyberpeace.rtks.service.bo.situation.SkillProgressBo;

public interface EngageService {

	/**
	 * 后台 获取所有 Engage
	 * @return
	 */
	List<EngageCategoryBo<EngageBackBo>> getEngages();

	/**
	 * 后台 获取Engage 详情
	 * @param resourceId
	 * @return
	 */
	EngageDetail getDetailById(String resourceId);

	/**
	 * 更新 engage 和后台训练模板关系
	 * @param resourceId
	 * @param businessTemplateIds
	 */
	void updateRelations(String resourceId, List<String> businessTemplateIds);

	/**
	 * 前台获取训练关联的 Engage
	 * @return
	 */
	List<EngageCategoryBo<EngageFrontBo>> getEngagesByTraining(String resourceId);

	/**
	 * 获取所有relation
	 *
	 * @return {@code Map<String, List<String>>}
	 */
	Map<String, List<String>> getAllRel();

	/**
	 * 获取engage进度
	 *
	 * @param userId 用户id
	 * @return {@code Map<String, List<String>>}
	 */
	SkillProgressBo getEngageProgress(String userId);
}
