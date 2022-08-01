package com.cyberpeace.cr.teamtrain.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.teamtrain.controller.req.TeamTrainStatisticsDetailReq;
import com.cyberpeace.cr.teamtrain.service.bo.TeamOrganizationStatisticsBo;
import com.cyberpeace.cr.teamtrain.service.bo.TeamPersonalStatisticsBo;
import com.cyberpeace.cr.teamtrain.service.bo.TeamStatisticsDetailBo;

import java.util.List;

public interface TeamTrainStatisticsService {

	/**
	 * 获取团队训练个人排名TOP5
	 * @param
	 * @return
	 */
	List<TeamPersonalStatisticsBo> getAllPersonalRankStatistics();

	/**
	 * 获取团队训练组织排名TOP5
	 * @param
	 * @return
	 */
	List<TeamOrganizationStatisticsBo> getOrganizationRankStatistics();

	/**
	 * 获取团队训练统计详情
	 * @param
	 * @return
	 */
	ListRsp<TeamStatisticsDetailBo> getAllStatisticsDetail(TeamTrainStatisticsDetailReq detailReq, Page page);

}
