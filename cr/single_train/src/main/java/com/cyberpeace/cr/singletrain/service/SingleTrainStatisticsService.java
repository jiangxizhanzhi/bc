package com.cyberpeace.cr.singletrain.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.singletrain.controller.req.SingleTrainStatisticsDetailReq;
import com.cyberpeace.cr.singletrain.service.bo.SingleOrganizationStatisticsBo;
import com.cyberpeace.cr.singletrain.service.bo.SinglePersonalStatisticsBo;
import com.cyberpeace.cr.singletrain.service.bo.SingleStatisticsDetailBo;

/**
 * 单训练统计学Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/18
 * @since 1.0.0
 */
public interface SingleTrainStatisticsService{

	/**
	 * 获取单兵训练个人排名TOP5
	 *
	 * @return 单兵训练个人排名
	 * @throws
	 * @see List<SinglePersonalStatisticsBo>
	 */
	List<SinglePersonalStatisticsBo> getAllPersonalRankStatistics();

	/**
	 * 获取单兵训练组织排名TOP5
	 *
	 * @return 单兵训练组织排名
	 * @throws
	 * @see List<SingleOrganizationStatisticsBo>
	 */
	List<SingleOrganizationStatisticsBo> getOrganizationRankStatistics();

	/**
	 * 获取单兵训练统计详情
	 *
	 * @param detailReq 起止时间
	 * @param page      分页
	 * @return 单兵训练统计分页数据
	 * @throws
	 * @see SingleTrainStatisticsDetailReq
	 * @see Page
	 * @see ListRsp<SingleStatisticsDetailBo>
	 */
	ListRsp<SingleStatisticsDetailBo> getAllStatisticsDetail(SingleTrainStatisticsDetailReq detailReq, Page page);

}
