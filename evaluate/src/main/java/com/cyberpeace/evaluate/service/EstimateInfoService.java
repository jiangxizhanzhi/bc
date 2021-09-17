package com.cyberpeace.evaluate.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.evaluate.dao.entity.EstimateInfo;
import com.cyberpeace.evaluate.service.context.EstimateInfoContext;

public interface EstimateInfoService {

	String addEstimateInfo(EstimateInfoContext estimateInfoContext);

	/**
	 * 查询用户创建的评估
	 * */
	ListRsp<EstimateInfo> queryEstimates(Page pageBase);

	EstimateInfo getEstimates(String estimateId);

	void deleteEstimate(String estimateId);

}
