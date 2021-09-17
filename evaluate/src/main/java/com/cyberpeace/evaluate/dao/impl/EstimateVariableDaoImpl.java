package com.cyberpeace.evaluate.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cyberpeace.evaluate.dao.EstimateVariableDao;
import com.cyberpeace.evaluate.dao.entity.EstimateVariable;
import com.cyberpeace.evaluate.dao.mapper.EstimateVariableMapper;

@Repository
public class EstimateVariableDaoImpl implements EstimateVariableDao {

	@Autowired
	private EstimateVariableMapper estimateVariableMapper;

	@Override
	public EstimateVariable getById(String variableId) {
		EstimateVariable estimateVariable = estimateVariableMapper.getById(variableId);
		return estimateVariable;
	}

	@Override
	public void insert(EstimateVariable estimateVariable) {
		estimateVariableMapper.insert(estimateVariable);
	}

	@Override
	public List<EstimateVariable> selectBySchemeId(String schemeId) {
		return estimateVariableMapper.selectBySchemeId(schemeId);
	}

	@Override
	public void batchDelete(List<String> variableIds) {
		estimateVariableMapper.batchDelete(variableIds);
	}

	@Override
	public void deleteByScheme(String schemeId) {
		estimateVariableMapper.deleteByScheme(schemeId);
	}
}
