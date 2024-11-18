package com.cyberpeace.cr.crbase.service;

import java.util.List;

import com.cyberpeace.cs.service.context.BadgeBo;

/**
 * 靶场徽章Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
public interface CrBadgeService {

	/**
	 * 批量创建
	 *
	 * @param businessId 业务标识
	 * @param type       类型
	 * @param userIds    用户id列表
	 * @throws
	 * @see String
	 * @see Integer
	 * @see List<String>
	 */
	void createBatch(String businessId, Integer type, List<String> userIds);

	/**
	 * 查询是否应对该用户显示红点
	 *
	 * @param businessId 业务标识
	 * @param type       类型
	 * @param userId     用户id
	 * @return {@code Boolean}
	 * @throws
	 * @see String
	 * @see Integer
	 * @see String
	 * @see Boolean
	 */
	Boolean ifExist(String businessId, Integer type, String userId);

	/**
	 * 消除红点
	 *
	 * @param businessId 业务标识
	 * @param type       类型
	 * @param userId     用户id
	 * @throws
	 * @see String
	 * @see Integer
	 * @see String
	 */
	void removeBadge(String businessId, Integer type, String userId);

	/**
	 * 获取任务裁决红点
	 *
	 * @param roomId 房间id
	 * @return {@code BadgeBo}
	 * @throws
	 * @see String
	 * @see BadgeBo
	 */
	BadgeBo getEvaluationBadge(String roomId);

	/**
	 * 点击任务裁决，取消红点
	 *
	 * @param roomId 房间id
	 * @throws
	 * @see String
	 */
	void clickEvaluationBadge(String roomId);
}
