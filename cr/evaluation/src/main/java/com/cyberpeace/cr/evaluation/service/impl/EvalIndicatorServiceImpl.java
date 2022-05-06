package com.cyberpeace.cr.evaluation.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndicator;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalAggregationMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalCollectionMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalIndicatorMapper;
import com.cyberpeace.cr.evaluation.service.EvalIndicatorService;
import com.cyberpeace.cr.evaluation.service.context.EvalIndicatorContext;

/**
 *
 */
@Service
public class EvalIndicatorServiceImpl extends ServiceImpl<EvalIndicatorMapper, EvalIndicator> implements EvalIndicatorService {

	@Autowired
	private TransactionTemplate transactionTemplate;

	@Autowired
	private EvalIndicatorMapper evalIndicatorMapper;

	@Autowired
	private EvalAggregationMapper evalAggregationMapper;

	@Autowired
	private EvalCollectionMapper evalCollectionMapper;

	@Override
	public void saveBatchContext(List<EvalIndicatorContext> evalIndicators, String userId,
			List<String> collectionIds) {

		List<EvalIndicator> evalIndicatorList = evalIndicators.stream()
				.map(evalIndicatorContext -> {
					EvalIndicator evalIndicator = BeanUtils.copy(evalIndicatorContext,
							EvalIndicator.class);

					evalIndicator.setResourceId(evalIndicatorContext.getEvalIndicatorId());
					evalIndicator.setAggregationIds(
							JsonUtil.arrayToString(evalIndicatorContext.getAggregationIds()));
					evalIndicator.setCollectionIds(JsonUtil.arrayToString(collectionIds));
					evalIndicator.setCreateUserId(userId);

					return evalIndicator;
				}).collect(Collectors.toList());

		this.saveBatch(evalIndicatorList);
	}

	@Override
	public Boolean batchDelete(List<String> evalIndicatorIds) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
				List<String> evalAggregationIds = evalAggregationMapper
						.selectIdsByEvalIndicatorIds(evalIndicatorIds);
				List<String> evalCollectionIds = evalCollectionMapper
						.selectIdsByEvalIndicatorIds(evalIndicatorIds);

				evalIndicatorMapper.deleteBatchIds(evalIndicatorIds);
				if (evalAggregationIds.size() > 0) {
					evalAggregationMapper.deleteBatchIds(evalAggregationIds);
				}
				if (evalCollectionIds.size() > 0) {
					evalCollectionMapper.deleteBatchIds(evalCollectionIds);
				}
			}
		});

		return true;
	}
}
