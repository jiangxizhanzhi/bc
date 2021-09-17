package com.cyberpeace.evaluate.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cyberpeace.evaluate.dao.EstimateVariableOutDao;
import com.cyberpeace.evaluate.dao.entity.EstimateVariableOut;
import com.cyberpeace.evaluate.dao.mapper.EstimateVariableOutMapper;

@Repository
public class EstimateVariableOutDaoImpl implements EstimateVariableOutDao {

	@Autowired
	EstimateVariableOutMapper estimateVariableOutMapper;

	@Override
	public void insert(EstimateVariableOut estimateVariableOut) {
		estimateVariableOutMapper.insert(estimateVariableOut);
	}

	@Override
	public List<EstimateVariableOut> select(EstimateVariableOut input) {
		List<EstimateVariableOut> estimateVariableOutList = estimateVariableOutMapper.select(input);
		return estimateVariableOutList;
	}
}
