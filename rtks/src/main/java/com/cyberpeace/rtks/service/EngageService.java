package com.cyberpeace.rtks.service;

import java.util.List;

import com.cyberpeace.rtks.service.bo.EngageDetail;
import com.cyberpeace.rtks.service.bo.career.EngageBackBo;
import com.cyberpeace.rtks.service.bo.career.EngageCategoryBo;
import com.cyberpeace.rtks.service.bo.career.EngageFrontBo;
import com.cyberpeace.rtks.service.bo.career.FrameItemBase;

/**
 * EngageService
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
public interface EngageService {

	/**
	 * 后台 获取所有 Engage
	 *
	 * @return
	 * @throws
	 * @see List<EngageCategoryBo<EngageBackBo>>
	 */
	List<EngageCategoryBo<EngageBackBo>> getEngages();

	/**
	 * 后台 获取Engage 详情
	 *
	 * @param resourceId
	 * @return
	 * @throws
	 * @see String
	 * @see EngageDetail
	 */
	EngageDetail getDetailById(String resourceId);

	/**
	 * 更新 engage 和后台训练模板关系
	 *
	 * @param resourceId
	 * @param businessTemplateIds 业务模板id列表
	 * @throws
	 * @see String
	 * @see List<String>
	 */
	void updateRelations(String resourceId, List<String> businessTemplateIds);

	/**
	 * 前台获取训练关联的 Engage
	 *
	 * @param resourceId 资源id
	 * @return
	 * @throws
	 * @see String
	 * @see List<EngageCategoryBo<EngageFrontBo>>
	 */
	List<EngageCategoryBo<EngageFrontBo>> getEngagesByTraining(String resourceId);

	/**
	 * 获取engage缓存
	 *
	 * @return
	 * @throws
	 * @see List<EngageCategoryBo<FrameItemBase>>
	 */
	List<EngageCategoryBo<FrameItemBase>> getCache();
}
