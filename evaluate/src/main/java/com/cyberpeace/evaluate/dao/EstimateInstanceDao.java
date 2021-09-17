package com.cyberpeace.evaluate.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.evaluate.dao.entity.EstimateInstance;

public interface EstimateInstanceDao {

	void insert(EstimateInstance estimateInstance);

	EstimateInstance getByEstimateIdAndStatus(String estimateId, Integer status);

	void update(EstimateInstance estimateInstance);

	EstimateInstance getByInstanceId(String instanceId);

	IPage<EstimateInstance> getByEstimateIdAndSave(String estimateId, Integer save, Integer status,
												   Page pageBase);

	Integer getCountByEstimateIdAndSave(String estimateId, Integer save, Integer status);
}
