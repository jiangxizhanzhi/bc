package com.cyberpeace.evaluate.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.evaluate.dao.EstimateInstanceDao;
import com.cyberpeace.evaluate.dao.entity.EstimateInstance;
import com.cyberpeace.evaluate.dao.mapper.EstimateInstanceMapper;

@Repository
public class EstimateInstanceDaoImpl implements EstimateInstanceDao {

	@Autowired
	private EstimateInstanceMapper estimateInstanceMapper;

	@Override
	public void insert(EstimateInstance estimateInstance) {
		estimateInstanceMapper.insert(estimateInstance);
	}

	@Override
	public EstimateInstance getByEstimateIdAndStatus(String estimateId, Integer status) {
		EstimateInstance ret = estimateInstanceMapper.getByEstimateIdAndStatus(estimateId, status);
		return ret;
	}

	@Override
	public void update(EstimateInstance estimateInstance) {
		estimateInstanceMapper.update(estimateInstance);
	}

	@Override
	public EstimateInstance getByInstanceId(String instanceId) {
		EstimateInstance ret = estimateInstanceMapper.getByInstanceId(instanceId);
		return ret;
	}

	@Override
	public IPage<EstimateInstance> getByEstimateIdAndSave(String estimateId, Integer save,
														  Integer status, Page pageBase) {
		return estimateInstanceMapper.getByEstimateIdAndSave(pageBase, estimateId, save, status);
	}

	@Override
	public Integer getCountByEstimateIdAndSave(String estimateId, Integer save, Integer status) {
		return estimateInstanceMapper.getCountByEstimateIdAndSave(estimateId, save, status);
	}
}
