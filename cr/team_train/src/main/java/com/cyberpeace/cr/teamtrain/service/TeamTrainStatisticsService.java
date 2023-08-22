package com.cyberpeace.cr.teamtrain.service;

import com.alibaba.fastjson.JSONObject;
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
	 * @return 团队训练个人排名
	 */
	List<TeamPersonalStatisticsBo> getAllPersonalRankStatistics(Integer teamCategory);

	/**
	 * 获取团队训练组织排名TOP5
	 * @return 团队训练组织排名
	 */
	List<TeamOrganizationStatisticsBo> getOrganizationRankStatistics(Integer teamCategory);

	/**
	 * 获取团队训练统计详情
	 * @param detailReq 起止时间
	 * @param page 分页
	 * @return 团队训练统计详情
	 */
	ListRsp<TeamStatisticsDetailBo> getAllStatisticsDetail(TeamTrainStatisticsDetailReq detailReq, Page page, Integer teamCategory);


	JSONObject getFlowAttackCount(String roomId);
}
