package com.cyberpeace.cr.redvsblue.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.redvsblue.controller.req.RbStatisticsDetailReq;
import com.cyberpeace.cr.redvsblue.service.bo.RbOrganizationStatisticsBo;
import com.cyberpeace.cr.redvsblue.service.bo.RbPersonalStatisticsBo;
import com.cyberpeace.cr.redvsblue.service.bo.RbStatisticsDetailBo;

import java.util.List;

public interface RbStatisticsService {

	/**
	 * 获取红蓝对抗个人排名TOP5
	 * @param
	 * @return
	 */
	List<RbPersonalStatisticsBo> getAllPersonalRankStatistics();

	/**
	 * 获取红蓝对抗组织排名TOP5
	 * @param
	 * @return
	 */
	List<RbOrganizationStatisticsBo> getOrganizationRankStatistics();

	/**
	 * 获取红蓝对抗统计详情
	 * @param
	 * @return
	 */
	ListRsp<RbStatisticsDetailBo> getAllStatisticsDetail(RbStatisticsDetailReq detailReq, Page page);

}
