package com.cyberpeace.rtks.service;

import java.util.List;

import com.cyberpeace.rtks.service.bo.career.*;

/**
 * 攻击集成电路Service
 *
 * @author SN
 * @date 2024/11/07
 */
public interface AttackIcsService {

	/**
	 * 获取全部 ATT&CK 和关联模版个数
	 *
	 * @return
	 */
	List<AttackCategoryBo<AttackBackBo>> getAttacks();

	/**
	 * 获取全部 ATT&CK， 当前训练关联的高亮
	 *
	 * @param businessTemplateId 业务模板id
	 * @return
	 */
	List<AttackCategoryBo<AttackFrontBo>> getAttacksByTraining(String businessTemplateId);

	/**
	 * 读取 ATT&CK 详情
	 *
	 * @param resourceId
	 * @return
	 */
	AttackDetail getDetailById(String resourceId);

	/**
	 * 更新ATT&CK-场景模板关联
	 *
	 * @param resourceId
	 * @param businessTemplateIds 业务模板id列表
	 */
	void updateRelations(String resourceId, List<String> businessTemplateIds);

	/**
	 * 获取缓存
	 *
	 * @return {@code List<AttackCategoryBo<FrameItemBase>>}
	 */
	List<AttackCategoryBo<FrameItemBase>> getCache();
}
