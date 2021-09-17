package com.cyberpeace.evaluate.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cyberpeace.evaluate.dao.EstimateVariableModelOutDao;
import com.cyberpeace.evaluate.dao.entity.EstimateVariableModelOut;
import com.cyberpeace.evaluate.dao.mapper.EstimateVariableModelOutMapper;
import org.springframework.stereotype.Service;

@Service
public class EstimateVariableModelOutDaoImpl implements EstimateVariableModelOutDao {

	@Autowired
	private EstimateVariableModelOutMapper estimateVariableModelOutMapper;

	@Override
	public void insert(EstimateVariableModelOut estimateVariableModelOut) {
		estimateVariableModelOutMapper.insert(estimateVariableModelOut);
	}

	@Override
	public List<EstimateVariableModelOut> select(EstimateVariableModelOut estimateVariableModelOut) {
		List<EstimateVariableModelOut> ret = estimateVariableModelOutMapper.select(estimateVariableModelOut);
		return ret;
	}

	@Override
	public void batchAdd(List<EstimateVariableModelOut> modelOutList) {
		if(modelOutList.isEmpty()){
			return ;
		}
		estimateVariableModelOutMapper.batchAdd(modelOutList);
	}
}
