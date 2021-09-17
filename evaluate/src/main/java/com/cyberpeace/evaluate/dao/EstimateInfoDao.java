package com.cyberpeace.evaluate.dao;

import com.cyberpeace.evaluate.dao.entity.EstimateInfo;

public interface EstimateInfoDao {

	/**
	 * 增加一条评估记录
	 * @param estimateInfo
	 *
	 * */
	void insert(EstimateInfo estimateInfo);

	/**
	 * 根据评估id查询一条记录
	 *
	 * */
	EstimateInfo getByEstimateId(String estimateId);

	void deleteById(String estimateId);
}
