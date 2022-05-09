package com.cyberpeace.cr.evaluation.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAlgorithm;
import com.cyberpeace.cr.evaluation.dao.mapper.*;
import com.cyberpeace.cr.evaluation.service.EvalAlgorithmService;

/**
 *
 */
@Service
public class EvalAlgorithmServiceImpl extends ServiceImpl<EvalAlgorithmMapper, EvalAlgorithm> implements EvalAlgorithmService {

	@Autowired
	EvalAlgorithmMapper evalAlgorithmMapper;

	@Autowired
	EvalAggregationMapper evalAggregationMapper;

	@Autowired
	EvalCollectionMapper evalCollectionMapper;

	@Autowired
	EvalIndexMapper evalIndexMapper;

	@Autowired
	EvalIndicatorMapper evalIndicatorMapper;

	@Override
	public Integer selectUsedNum(List<String> ids) {
		Integer num = evalAggregationMapper.selectAlgorithmNum(ids)
				+ evalCollectionMapper.selectAlgorithmSum(ids)
				+ evalIndexMapper.selectAlgorithmSum(ids)
				+ evalIndicatorMapper.selectAlgorithmSum(ids);
		return num;
	}

	@Override
	public Boolean delete(List<String> ids) {

		evalAlgorithmMapper.deleteBatchIds(ids);
		return true;
	}

	@Override
	public void updateStateById(String resourceId) {
		evalAlgorithmMapper.updateStateById(resourceId);
	}
}
