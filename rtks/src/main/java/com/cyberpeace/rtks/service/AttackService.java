package com.cyberpeace.rtks.service;

import java.util.List;

import com.cyberpeace.rtks.service.bo.career.*;

/**
 * 攻击Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
public interface AttackService {

	/**
	 * 获取全部 ATT&CK 和关联模版个数
	 *
	 * @return
	 * @throws
	 * @see List<AttackCategoryBo<AttackBackBo>>
	 */
	List<AttackCategoryBo<AttackBackBo>> getAttacks();

	/**
	 * 获取全部 ATT&CK， 当前训练关联的高亮
	 *
	 * @param businessTemplateId 业务模板id
	 * @return
	 * @throws
	 * @see String
	 * @see List<AttackCategoryBo<AttackFrontBo>>
	 */
	List<AttackCategoryBo<AttackFrontBo>> getAttacksByTraining(String businessTemplateId);

	/**
	 * 读取 ATT&CK 详情
	 *
	 * @param resourceId
	 * @return
	 * @throws
	 * @see String
	 * @see AttackDetail
	 */
	AttackDetail getDetailById(String resourceId);

	/**
	 * 更新ATT&CK-场景模板关联
	 *
	 * @param resourceId
	 * @param businessTemplateIds 业务模板id列表
	 * @throws
	 * @see String
	 * @see List<String>
	 */
	void updateRelations(String resourceId, List<String> businessTemplateIds);

	/**
	 * 获取ATT&CK的缓存
	 *
	 * @return
	 * @throws
	 * @see List<AttackCategoryBo<FrameItemBase>>
	 */
	List<AttackCategoryBo<FrameItemBase>> getCache();
}
