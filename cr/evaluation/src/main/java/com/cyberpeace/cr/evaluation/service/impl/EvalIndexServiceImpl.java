package com.cyberpeace.cr.evaluation.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndex;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalIndexMapper;
import com.cyberpeace.cr.evaluation.service.EvalIndexService;
import com.cyberpeace.cr.evaluation.service.context.IndexContext;

/**
 *
 */
@Service
public class EvalIndexServiceImpl extends ServiceImpl<EvalIndexMapper, EvalIndex> implements EvalIndexService {

	@Autowired
	private EvalIndexMapper evalIndexMapper;

	@Override
	public List<IndexContext> getIndexesByEvalSystemId(String systemId, Integer level) {

		List<EvalIndex> evalIndexList = evalIndexMapper.selectListBySystemIdAndLevel(systemId,
				level);

		return evalIndexList.stream().map(evalIndex -> {
			IndexContext indexContext = BeanUtils.copy(evalIndex, IndexContext.class);

			indexContext.setIndexId(evalIndex.getResourceId());
			indexContext.setChildrenIds(
					JsonUtil.toJsonObjectArray(evalIndex.getChildrenIds(), String.class));
			indexContext.setAlgorithmId(evalIndex.getAggregatingAlgorithmId());
			indexContext.setParams(evalIndex.getAggregatingAlgorithmParams());

			return indexContext;
		}).collect(Collectors.toList());
	}

	@Override
	public List<IndexContext> getIndexesByEvalSystemId(String systemId, Integer level, Boolean isTest) {

		List<EvalIndex> evalIndexList = evalIndexMapper.selectListBySystemIdAndLevel(systemId,
				level);

		return evalIndexList.stream().map(evalIndex -> {
			IndexContext indexContext = BeanUtils.copy(evalIndex, IndexContext.class);

			indexContext.setIndexId(evalIndex.getResourceId());
			indexContext.setChildrenIds(
					JsonUtil.toJsonObjectArray(evalIndex.getChildrenIds(), String.class));
			indexContext.setAlgorithmId(evalIndex.getAggregatingAlgorithmId());
			indexContext.setParams(evalIndex.getAggregatingAlgorithmParams());
			if (Boolean.TRUE.equals(isTest)) {
				indexContext.setScore(evalIndex.getTestScore());
				indexContext.setMessage(evalIndex.getTestMessage());
			} else {
				indexContext.setScore(evalIndex.getCalScore());
				indexContext.setMessage(evalIndex.getCalMessage());
			}
			return indexContext;
		}).collect(Collectors.toList());
	}

	@Override
	public void deleteBatchByEvalSystemId(String evalSystemId) {
		evalIndexMapper.deleteBatchByEvalSystemId(evalSystemId);
	}
}
