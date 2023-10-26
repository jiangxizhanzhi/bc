package com.cyberpeace.cr.evaluation.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAggregation;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalAggregationMapper;
import com.cyberpeace.cr.evaluation.service.EvalAggregationService;
import com.cyberpeace.cr.evaluation.service.context.EvalAggregationContext;
import com.google.common.collect.Lists;

/**
 *
 */
@Service
public class EvalAggregationServiceImpl extends ServiceImpl<EvalAggregationMapper, EvalAggregation> implements EvalAggregationService {

	@Autowired
	EvalAggregationMapper evalAggregationMapper;

	@Override
	public void saveBatchContext(List<EvalAggregationContext> evalAggregations) {
		List<EvalAggregation> evalAggregationList = evalAggregations.stream()
				.map(evalAggregationContext -> {
					EvalAggregation evalAggregation = BeanUtils.copy(evalAggregationContext,
							EvalAggregation.class);

					evalAggregation.setResourceId(evalAggregationContext.getEvalAggregationId());
					evalAggregation.setCollectionIds(
							JsonUtil.arrayToString(evalAggregationContext.getAggregationIds()));

					return evalAggregation;
				}).collect(Collectors.toList());

		this.saveBatch(evalAggregationList);
	}

	@Override
	public List<EvalAggregationContext> getContextByEvalIndicatorId(String evalIndicatorId) {
		List<EvalAggregation> evalAggregationList = evalAggregationMapper
				.selectIdsByEvalIndicatorIds(Lists.newArrayList(evalIndicatorId));

		return evalAggregationList.stream()
				.map(evalAggregation -> {
					EvalAggregationContext evalAggregationContext = BeanUtils.copy(evalAggregation,
							EvalAggregationContext.class);

					evalAggregationContext.setEvalAggregationId(evalAggregation.getResourceId());
					evalAggregationContext.setAggregationIds(JsonUtil
							.toJsonObjectArray(evalAggregation.getCollectionIds(), String.class));

					return evalAggregationContext;
				}).collect(Collectors.toList());
	}
}
