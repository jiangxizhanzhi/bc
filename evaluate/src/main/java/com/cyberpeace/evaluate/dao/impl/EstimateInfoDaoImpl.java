package com.cyberpeace.evaluate.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cyberpeace.evaluate.dao.EstimateInfoDao;
import com.cyberpeace.evaluate.dao.entity.EstimateInfo;
import com.cyberpeace.evaluate.dao.mapper.EstimateInfoMapper;

@Repository
public class EstimateInfoDaoImpl implements EstimateInfoDao {

	@Autowired
	private EstimateInfoMapper estimateInfoMapper;

	@Override
	public void insert(EstimateInfo estimateInfo) {
		estimateInfoMapper.insert(estimateInfo);
	}

	@Override
	public EstimateInfo getByEstimateId(String estimateId) {
		EstimateInfo estimateInfo = estimateInfoMapper.getByEstimateId(estimateId);
		return estimateInfo;
	}

	@Override
	public void deleteById(String estimateId) {
		estimateInfoMapper.deleteById(estimateId);
	}
}
