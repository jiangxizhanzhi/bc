package com.cyberpeace.cr.redvsblue.service;

import java.util.List;

import com.cyberpeace.cr.redvsblue.service.bo.RbOrganizationStatisticsBo;
import com.cyberpeace.cr.redvsblue.service.bo.RbPersonalStatisticsBo;

public interface RbStatisticsService {

	/**
	 * 获取红蓝对抗个人排名TOP5
	 * @return 红蓝对抗个人排名
	 */
	List<RbPersonalStatisticsBo> getAllPersonalRankStatistics();

	/**
	 * 获取红蓝对抗组织排名TOP5
	 * @return 红蓝对抗组织排名
	 */
	List<RbOrganizationStatisticsBo> getOrganizationRankStatistics();

}
