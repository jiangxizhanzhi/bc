package com.cyberpeace.rtks.service;

import java.util.List;
import java.util.Map;

import com.cyberpeace.rtks.service.bo.career.AttackBackBo;
import com.cyberpeace.rtks.service.bo.career.AttackCategoryBo;
import com.cyberpeace.rtks.service.bo.career.AttackDetail;
import com.cyberpeace.rtks.service.bo.career.AttackFrontBo;
import com.cyberpeace.rtks.service.bo.situation.SkillProgressBo;
import com.cyberpeace.rtks.service.bo.career.*;

public interface AttackService {

	/**
	 * 获取全部 ATT&CK 和关联模版个数
	 * @return
	 */
	List<AttackCategoryBo<AttackBackBo>> getAttacks();

	/**
	 * 获取全部 ATT&CK， 当前训练关联的高亮
	 * @param
	 * @return
	 */
	List<AttackCategoryBo<AttackFrontBo>> getAttacksByTraining(String businessTemplateId);

	/**
	 * 读取 ATT&CK 详情
	 * @param resourceId
	 * @return
	 */
	AttackDetail getDetailById(String resourceId);

	/**
	 * 更新ATT&CK-场景模板关联
	 * @param resourceId
	 * @param businessTemplateIds
	 */
	void updateRelations(String resourceId, List<String> businessTemplateIds);

	/**
	 * 获取ATT&CK的缓存
	 * @return
	 */
	List<AttackCategoryBo<FrameItemBase>> getCache();

	/**
	 * 获取所有relation
	 *
	 * @return {@code Map<String, List<String>>}
	 */
	Map<String, List<String>> getAllRel();

	/**
	 * 计数所有
	 *
	 * @return {@code Integer}
	 */
	Integer countAll();

	/**
	 * 获取attack进展
	 *
	 * @param userId 用户id
	 * @return {@code SkillProgressBo}
	 */
	SkillProgressBo getAttackProgress(String userId);
}
