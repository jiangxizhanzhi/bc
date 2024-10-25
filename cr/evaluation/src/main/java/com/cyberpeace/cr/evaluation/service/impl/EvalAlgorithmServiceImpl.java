package com.cyberpeace.cr.evaluation.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAlgorithm;
import com.cyberpeace.cr.evaluation.dao.mapper.*;
import com.cyberpeace.cr.evaluation.service.EvalAlgorithmService;
import com.cyberpeace.cr.evaluation.service.context.EvalListQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

	@Override
	public List<EvalAlgorithm> selectByType(String type) {
		return evalAlgorithmMapper.selectByType(type);
	}

	@Override
	public IPage<EvalAlgorithm> page(EvalListQuery listQuery) {

		Page page = listQuery.toPageParam();

        return evalAlgorithmMapper.listEvalAlgorithm(page, listQuery);
	}

	@Override
	public List<String> selectUsedAlgorithmIds(List<String> ids) {
		List<String> usedAlgorithmIds = evalAggregationMapper.selectUsedAlgorithmIds(ids);

		usedAlgorithmIds.addAll(evalCollectionMapper.selectUsedAlgorithmIds(ids));
		usedAlgorithmIds.addAll(evalIndexMapper.selectUsedAlgorithmIds(ids));
		usedAlgorithmIds.addAll(evalIndicatorMapper.selectUsedAlgorithmIds(ids));

		return usedAlgorithmIds.stream().distinct().collect(Collectors.toList());
	}

	@Override
	public void updateStateByIds(List<String> algorithmIds) {
		evalAlgorithmMapper.updateStateByIds(algorithmIds);
	}
}
