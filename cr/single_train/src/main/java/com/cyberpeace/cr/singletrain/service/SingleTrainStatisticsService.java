package com.cyberpeace.cr.singletrain.service;

import java.util.List;

import com.cyberpeace.cr.singletrain.service.bo.SingleOrganizationStatisticsBo;
import com.cyberpeace.cr.singletrain.service.bo.SinglePersonalStatisticsBo;

public interface SingleTrainStatisticsService{

	/**
	 * 获取单兵训练个人排名TOP5
	 * @return 单兵训练个人排名
	 */
	List<SinglePersonalStatisticsBo> getAllPersonalRankStatistics();

	/**
	 * 获取单兵训练组织排名TOP5
	 * @return 单兵训练组织排名
	 */
	List<SingleOrganizationStatisticsBo> getOrganizationRankStatistics();

}
