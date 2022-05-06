package com.cyberpeace.cr.evaluation.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAggregation;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalAggregationMapper;
import com.cyberpeace.cr.evaluation.service.EvalAggregationService;
import com.cyberpeace.cr.evaluation.service.context.EvalAggregationContext;

/**
 *
 */
@Service
public class EvalAggregationServiceImpl extends ServiceImpl<EvalAggregationMapper, EvalAggregation> implements EvalAggregationService {

	@Override
	public void saveBatchContext(List<EvalAggregationContext> evalAggregations) {
		List<EvalAggregation> evalAggregationList = evalAggregations.stream()
				.map(evalAggregationContext -> {
					EvalAggregation evalAggregation = BeanUtils.copy(evalAggregationContext,
							EvalAggregation.class);

					evalAggregation.setResourceId(evalAggregationContext.getEvalAggregationId());
					evalAggregation.setCollectionIds(
							JsonUtil.arrayToString(evalAggregationContext.getCollectionIds()));

					return evalAggregation;
				}).collect(Collectors.toList());

		this.saveBatch(evalAggregationList);
	}
}
