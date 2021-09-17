package com.cyberpeace.evaluate.dao;

import com.cyberpeace.evaluate.dao.entity.EstimateIndexOut;

public interface EstimateIndexOutDao {

	void insert(EstimateIndexOut estimateIndexOut);

	EstimateIndexOut select(EstimateIndexOut estimateIndexOut);
}
