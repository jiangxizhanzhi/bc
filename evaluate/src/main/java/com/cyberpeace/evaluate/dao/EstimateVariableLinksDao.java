package com.cyberpeace.evaluate.dao;

import java.util.List;

import com.cyberpeace.evaluate.dao.entity.EstimateVariableLinks;

public interface EstimateVariableLinksDao {

	void inset(EstimateVariableLinks link);

	List<EstimateVariableLinks> getByEstimateId(String estimateId);

	EstimateVariableLinks getOneLink(String estimateId, String variableId);
}
