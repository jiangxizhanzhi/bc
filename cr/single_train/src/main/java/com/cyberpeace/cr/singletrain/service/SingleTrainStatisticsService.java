package com.cyberpeace.cr.singletrain.service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrTask;
import com.cyberpeace.cr.crbase.service.impl.bo.BaseInfo;
import com.cyberpeace.cr.singletrain.controller.req.SingleTrainStatisticsDetailReq;
import com.cyberpeace.cr.singletrain.dao.entity.SingleTemplate;
import com.cyberpeace.cr.singletrain.service.bo.SingleOrganizationStatisticsBo;
import com.cyberpeace.cr.singletrain.service.bo.SinglePersonalStatisticsBo;
import com.cyberpeace.cr.singletrain.service.bo.SingleStatisticsDetailBo;
import com.cyberpeace.cr.singletrain.service.bo.SingleTemplateBo;

import java.util.List;

public interface SingleTrainStatisticsService{

	/**
	 * 获取单兵训练个人排名TOP5
	 * @param
	 * @return
	 */
	List<SinglePersonalStatisticsBo> getAllPersonalRankStatistics();

	/**
	 * 获取单兵训练组织排名TOP5
	 * @param
	 * @return
	 */
	List<SingleOrganizationStatisticsBo> getOrganizationRankStatistics();

	/**
	 * 获取单兵训练统计详情
	 * @param
	 * @return
	 */
	ListRsp<SingleStatisticsDetailBo> getAllStatisticsDetail(SingleTrainStatisticsDetailReq detailReq, Page page);

}
