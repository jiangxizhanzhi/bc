package com.cyberpeace.evaluate.dao;

import java.util.List;

import com.cyberpeace.evaluate.dao.entity.AlgorithmParamLink;

public interface AlgorithmParamLinkDao {

	void batchAdd(List<AlgorithmParamLink> paramLinks);

	List<AlgorithmParamLink> selectByAlgorithm(String algorithmId);

	void deleteByAlgorithm(String algorithmId);
}
