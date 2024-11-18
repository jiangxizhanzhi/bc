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
 * 评估集合体Service实现
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see ServiceImpl
 * @see EvalAggregationService
 * @since 1.0.0
 */
@Service
public class EvalAggregationServiceImpl extends ServiceImpl<EvalAggregationMapper, EvalAggregation> implements EvalAggregationService {

	/**
	 * 评估集合体Mapper
	 *
	 * @see EvalAggregationMapper
	 */
	@Autowired
	EvalAggregationMapper evalAggregationMapper;

	/**
	 * 保存批量上下文
	 *
	 * @param evalAggregations eval 聚合
	 * @throws
	 * @see List<EvalAggregationContext>
	 */
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

	/**
	 * 获取上下文由评估指示器id
	 *
	 * @param evalIndicatorId 评估指示器id
	 * @return {@code List<EvalAggregationContext> }
	 * @throws
	 * @see String
	 * @see List<EvalAggregationContext>
	 */
	@Override
	public List<EvalAggregationContext> getContextByEvalIndicatorId(String evalIndicatorId) {
		List<EvalAggregation> evalAggregationList = evalAggregationMapper
				.selectIdsByEvalIndicatorIds(Lists.newArrayList(evalIndicatorId));

		List<EvalAggregationContext> evalAggregationContexts = evalAggregationList.stream()
				.map(evalAggregation -> {
					EvalAggregationContext evalAggregationContext = BeanUtils.copy(evalAggregation,
							EvalAggregationContext.class);

					evalAggregationContext.setEvalAggregationId(evalAggregation.getResourceId());
					evalAggregationContext.setAggregationIds(JsonUtil
							.toJsonObjectArray(evalAggregation.getCollectionIds(), String.class));

					return evalAggregationContext;
				}).collect(Collectors.toList());

		return evalAggregationContexts;
	}
}
