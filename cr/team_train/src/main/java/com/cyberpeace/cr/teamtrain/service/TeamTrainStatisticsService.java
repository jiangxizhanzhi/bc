package com.cyberpeace.cr.teamtrain.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.teamtrain.controller.req.TeamTrainStatisticsDetailReq;
import com.cyberpeace.cr.teamtrain.service.bo.TeamOrganizationStatisticsBo;
import com.cyberpeace.cr.teamtrain.service.bo.TeamPersonalStatisticsBo;
import com.cyberpeace.cr.teamtrain.service.bo.TeamStatisticsDetailBo;

public interface TeamTrainStatisticsService {

	/**
	 * 获取团队训练个人排名TOP5
	 * @return 团队训练个人排名
	 */
	List<TeamPersonalStatisticsBo> getAllPersonalRankStatistics();

	/**
	 * 获取团队训练组织排名TOP5
	 * @return 团队训练组织排名
	 */
	List<TeamOrganizationStatisticsBo> getOrganizationRankStatistics();



}
