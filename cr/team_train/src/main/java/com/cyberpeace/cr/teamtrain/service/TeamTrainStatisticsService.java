package com.cyberpeace.cr.teamtrain.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.teamtrain.controller.req.TeamTrainStatisticsDetailReq;
import com.cyberpeace.cr.teamtrain.service.bo.TeamOrganizationStatisticsBo;
import com.cyberpeace.cr.teamtrain.service.bo.TeamPersonalStatisticsBo;
import com.cyberpeace.cr.teamtrain.service.bo.TeamStatisticsDetailBo;

/**
 * 团队训练统计学Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/18
 * @since 1.0.0
 */
public interface TeamTrainStatisticsService {

	/**
	 * 获取团队训练个人排名TOP5
	 *
	 * @return 团队训练个人排名
	 * @throws
	 * @see List<TeamPersonalStatisticsBo>
	 */
	List<TeamPersonalStatisticsBo> getAllPersonalRankStatistics();

	/**
	 * 获取团队训练组织排名TOP5
	 *
	 * @return 团队训练组织排名
	 * @throws
	 * @see List<TeamOrganizationStatisticsBo>
	 */
	List<TeamOrganizationStatisticsBo> getOrganizationRankStatistics();

	/**
	 * 获取团队训练统计详情
	 *
	 * @param detailReq 起止时间
	 * @param page      分页
	 * @return 团队训练统计详情
	 * @throws
	 * @see TeamTrainStatisticsDetailReq
	 * @see Page
	 * @see ListRsp<TeamStatisticsDetailBo>
	 */
	ListRsp<TeamStatisticsDetailBo> getAllStatisticsDetail(TeamTrainStatisticsDetailReq detailReq, Page page);

}
