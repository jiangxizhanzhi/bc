package com.cyberpeace.evaluate.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cyberpeace.evaluate.dao.EstimateItemsOutDao;
import com.cyberpeace.evaluate.dao.entity.EstimateItemsOut;
import com.cyberpeace.evaluate.dao.mapper.EstimateItemsOutMapper;

@Repository
public class EstimateItemsOutDaoImpl implements EstimateItemsOutDao {

	@Autowired
	private EstimateItemsOutMapper estimateItemsOutMapper;

	@Override
	public void insert(EstimateItemsOut estimateItemsOut) {
		estimateItemsOutMapper.insert(estimateItemsOut);
	}
}
