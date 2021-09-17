package com.cyberpeace.evaluate.dao;

import java.util.List;

import com.cyberpeace.evaluate.dao.entity.EstimateVariableOut;

public interface EstimateVariableOutDao {

	void insert(EstimateVariableOut estimateVariableOut);

	List<EstimateVariableOut> select(EstimateVariableOut input);

}
