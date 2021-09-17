package com.cyberpeace.evaluate.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.evaluation.ProbeOriginData;
import com.cyberpeace.evaluate.dao.entity.EstimateInstance;
import com.cyberpeace.evaluate.service.bo.EstimateInstanceBO;
import com.cyberpeace.evaluate.service.context.EstimateInstanceContext;
import com.cyberpeace.evaluate.service.bo.EstimateInstanceViewBO;
import com.cyberpeace.evaluate.service.bo.EstimateReviewBo;

public interface EstimateInstanceService {

	/**
	 * 手动保存评估实例记录
	 * */
	EstimateInstance saveEstimateInstanceResult(EstimateInstanceContext estimateInstanceContext);

	EstimateInstance destroyEstimateInstanceResult(EstimateInstanceContext estimateInstanceContext);

	/**
	 * 评估停止采集数据
	 * */
	void stopEstimateInstance(String instanceId);

	/**
	 * 根据评估id，查询进行中的评估实例
	 * */
	EstimateInstanceViewBO getEstimateInstance(String estimateId);

	/**
	 * 创建一个评估实例
	 * */
	EstimateInstance createEstimateInstance(EstimateInstanceContext estimateInstanceContext);

	/**
	 * 查询评估实例的格式化变量数据
	 * */
	List<EstimateReviewBo> reviewEstimateInstanceFormalData(String instanceId);

	EstimateReviewBo reviewEstimateInstanceModelData(String instanceId, String variableId);

	ListRsp<ProbeOriginData> reviewEstimateInstanceOriginData(String instanceId, String variableId,
															  Page pageBase);

	/**
	 * 查询评估对应的实例结果
	 * */
	ListRsp<EstimateInstanceBO> reviewEstimateInstanceResults(String estimateId, Page page);

	/**
	 * 计算并保存评估结果
	 * */
	void calculateAndSave(String instanceId);
}
