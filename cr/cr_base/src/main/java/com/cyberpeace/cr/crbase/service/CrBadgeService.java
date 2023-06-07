package com.cyberpeace.cr.crbase.service;

import java.util.List;

public interface CrBadgeService {

	/**
	 * 批量创建
	 *
	 * @param businessId 业务标识
	 * @param type       类型
	 * @param userIds    用户id列表
	 */
	void createBatch(String businessId, Integer type, List<String> userIds);

	/**
	 * 查询是否应对该用户显示红点
	 *
	 * @param businessId 业务标识
	 * @param type       类型
	 * @param userId     用户id
	 * @return {@code Boolean}
	 */
	Boolean ifExist(String businessId, Integer type, String userId);

	/**
	 * 消除红点
	 *
	 * @param businessId 业务标识
	 * @param type       类型
	 * @param userId     用户id
	 */
	void removeBadge(String businessId, Integer type, String userId);
}
