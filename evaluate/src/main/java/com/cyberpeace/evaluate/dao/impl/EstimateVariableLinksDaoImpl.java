package com.cyberpeace.evaluate.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cyberpeace.evaluate.dao.EstimateVariableLinksDao;
import com.cyberpeace.evaluate.dao.entity.EstimateVariableLinks;
import com.cyberpeace.evaluate.dao.mapper.EstimateVariableLinksMapper;

@Repository
public class EstimateVariableLinksDaoImpl implements EstimateVariableLinksDao {

	@Autowired
	private EstimateVariableLinksMapper estimateVariableLinksMapper;

	@Override
	public void inset(EstimateVariableLinks link) {
		estimateVariableLinksMapper.insert(link);
	}

	@Override
	public List<EstimateVariableLinks> getByEstimateId(String estimateId) {
		List<EstimateVariableLinks> linksList = estimateVariableLinksMapper
				.selectByEstimateId(estimateId);
		return linksList;
	}

	@Override
	public EstimateVariableLinks getOneLink(String estimateId, String variableId) {
		EstimateVariableLinks estimateVariableLinks = estimateVariableLinksMapper
				.getOneLink(estimateId, variableId);
		return estimateVariableLinks;
	}
}
