package com.cyberpeace.cr.redvsblue.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.redvsblue.controller.req.RbStatisticsDetailReq;
import com.cyberpeace.cr.redvsblue.service.bo.RbOrganizationStatisticsBo;
import com.cyberpeace.cr.redvsblue.service.bo.RbPersonalStatisticsBo;
import com.cyberpeace.cr.redvsblue.service.bo.RbStatisticsDetailBo;

/**
 * 红蓝对抗统计学Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/18
 * @since 1.0.0
 */
public interface RbStatisticsService {

	/**
	 * 获取红蓝对抗个人排名TOP5
	 *
	 * @return 红蓝对抗个人排名
	 * @throws
	 * @see List<RbPersonalStatisticsBo>
	 */
	List<RbPersonalStatisticsBo> getAllPersonalRankStatistics();

	/**
	 * 获取红蓝对抗组织排名TOP5
	 *
	 * @return 红蓝对抗组织排名
	 * @throws
	 * @see List<RbOrganizationStatisticsBo>
	 */
	List<RbOrganizationStatisticsBo> getOrganizationRankStatistics();

	/**
	 * 获取红蓝对抗统计详情
	 *
	 * @param detailReq 起止时间
	 * @param page      分页
	 * @return 红蓝对抗统计详情
	 * @throws
	 * @see RbStatisticsDetailReq
	 * @see Page
	 * @see ListRsp<RbStatisticsDetailBo>
	 */
	ListRsp<RbStatisticsDetailBo> getAllStatisticsDetail(RbStatisticsDetailReq detailReq, Page page);

}
