package com.cyberpeace.evaluate.dao;

import java.util.List;

import com.cyberpeace.evaluate.dao.entity.EstimateAlgorithm;

public interface EstimateAlgorithmDao {

	void insert(EstimateAlgorithm estimateAlgorithm);

	/**
	 * 跟据类型查询所有算法数据
	 * @return
	 */
	List<EstimateAlgorithm> selectByType(String algorithmType);

	void deleteById(String algorithmId);

	EstimateAlgorithm selectById(String algorithmId);
}
