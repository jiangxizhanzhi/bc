package com.cyberpeace.evaluate.dao.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.cyberpeace.evaluate.dao.EstimateAlgorithmDao;
import com.cyberpeace.evaluate.dao.entity.EstimateAlgorithm;
import com.cyberpeace.evaluate.dao.mapper.EstimateAlgorithmMapper;

@Repository
public class EstimateAlgorithmDaoImpl implements EstimateAlgorithmDao {

	@Autowired
	private EstimateAlgorithmMapper estimateAlgorithmMapper;

	@Override
	public void insert(EstimateAlgorithm estimateAlgorithm) {
		estimateAlgorithmMapper.insert(estimateAlgorithm);
	}

	@Override
	public List<EstimateAlgorithm> selectByType(String algorithmType) {
		if (StringUtils.equals(algorithmType, EstimateAlgorithm.ALL)) {
			return estimateAlgorithmMapper.selectByType(null);
		}
		return estimateAlgorithmMapper.selectByType(algorithmType);
	}

	@Override
	public void deleteById(String algorithmId) {
		estimateAlgorithmMapper.deleteById(algorithmId);
	}

	@Override
	public EstimateAlgorithm selectById(String algorithmId) {
		return estimateAlgorithmMapper.selectById(algorithmId);
	}
}
