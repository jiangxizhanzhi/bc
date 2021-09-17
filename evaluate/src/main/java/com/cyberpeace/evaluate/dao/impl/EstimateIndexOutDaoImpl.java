package com.cyberpeace.evaluate.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cyberpeace.evaluate.dao.EstimateIndexOutDao;
import com.cyberpeace.evaluate.dao.entity.EstimateIndexOut;
import com.cyberpeace.evaluate.dao.mapper.EstimateIndexOutMapper;

@Repository
public class EstimateIndexOutDaoImpl implements EstimateIndexOutDao {

	@Autowired
	private EstimateIndexOutMapper estimateIndexOutMapper;

	@Override
	public void insert(EstimateIndexOut estimateIndexOut) {
		estimateIndexOutMapper.insert(estimateIndexOut);
	}

	@Override
	public EstimateIndexOut select(EstimateIndexOut estimateIndexOut) {
		EstimateIndexOut ret = estimateIndexOutMapper.select(estimateIndexOut);
		return ret;
	}
}
