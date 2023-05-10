package com.cyberpeace.cr.evaluation.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.evaluation.controller.req.EvalIndicatorReq;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndexRef;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndicator;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalAggregationMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalCollectionMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalIndicatorMapper;
import com.cyberpeace.cr.evaluation.service.EvalIndicatorService;
import com.cyberpeace.cr.evaluation.service.context.EvalIndicatorContext;
import com.cyberpeace.cr.evaluation.service.context.EvalListQuery;

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
	public void saveBatchContext(EvalIndicatorReq evalIndicatorReq, String userId,
			List<String> collectionIds) {

		List<EvalIndicator> evalIndicatorList = evalIndicatorReq.getEvalIndicators().stream()
				.map(evalIndicatorContext -> {
					return convertEvalIndicatorReqToEvalIndicator(evalIndicatorContext,
							evalIndicatorReq, userId, collectionIds);
				}).collect(Collectors.toList());

		this.saveBatch(evalIndicatorList);
	}

	private EvalIndicator convertEvalIndicatorReqToEvalIndicator(
			EvalIndicatorContext evalIndicatorContext, EvalIndicatorReq evalIndicatorReq,
			String userId, List<String> collectionIds) {
		EvalIndicator evalIndicator = BeanUtils.copy(evalIndicatorContext, EvalIndicator.class);

		evalIndicator.setResourceId(evalIndicatorContext.getEvalIndicatorId());
		evalIndicator.setAggregationIds(
				JsonUtil.arrayToString(evalIndicatorContext.getAggregationIds()));
		evalIndicator.setCollectionIds(JsonUtil.arrayToString(collectionIds));
		evalIndicator.setCreateUserId(userId);
		evalIndicator.setDescription(evalIndicatorReq.getDescription());
		evalIndicator.setDatatype(evalIndicatorReq.getDatatype());
		evalIndicator.setAttribute(evalIndicatorReq.getAttribute());
		evalIndicator.setAttributeValue(evalIndicatorReq.getAttributeValue());
		evalIndicator.setConnlist(evalIndicatorReq.getConnlist());
		// 聚合算法为层次分析法时 保存矩阵数据内容
		List<EvalIndicatorContext> evalIndicators = evalIndicatorReq.getEvalIndicators();
		if(!evalIndicators.isEmpty() && CrCommonConstants.AHP_ID.equals(evalIndicators.get(0).getAggregatingAlgorithmId())){
			evalIndicator.setMatrixData(evalIndicators.get(0).getMatrixData());
		}

		if (evalIndicatorReq.getAttribute().equals(EvalIndexRef.DataAttr.COST.getCode())
				|| evalIndicatorReq.getAttribute()
						.equals(EvalIndexRef.DataAttr.BENEFIT.getCode())) {
			evalIndicator.setAttributeValue(null);
		}

		return evalIndicator;
	}

	@Override
	public Boolean batchDelete(List<String> evalIndicatorIds) {
		transactionTemplate.execute(new TransactionCallbackWithoutResult() {

			@Override
			protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {

				deleteAggregationAndCollection(evalIndicatorIds);
				evalIndicatorMapper.deleteBatchIds(evalIndicatorIds);

			}
		});

		return true;
	}

	@Override
	public void deleteAggregationAndCollection(List<String> evalIndicatorIds) {
		List<String> evalAggregationIds = StreamApiUtils.map(
				evalAggregationMapper.selectIdsByEvalIndicatorIds(evalIndicatorIds),
				evalAggregation -> evalAggregation.getResourceId());
		List<String> evalCollectionIds = StreamApiUtils.map(
				evalCollectionMapper.selectIdsByEvalIndicatorIds(evalIndicatorIds),
				evalCollectionContext -> evalCollectionContext.getResourceId());

		if (evalAggregationIds.size() > 0) {
			evalAggregationMapper.deleteBatchIds(evalAggregationIds);
		}
		if (evalCollectionIds.size() > 0) {
			evalCollectionMapper.deleteBatchIds(evalCollectionIds);
		}
	}

	@Override
	public void updateBatchContext(EvalIndicatorReq evalIndicatorReq,
			List<EvalIndicatorContext> evalIndicators, String userId, List<String> collectionIds) {
		List<EvalIndicator> evalIndicatorList = evalIndicators.stream()
				.map(evalIndicatorContext -> {
					return convertEvalIndicatorReqToEvalIndicator(evalIndicatorContext,
							evalIndicatorReq, userId, collectionIds);
				}).collect(Collectors.toList());

		this.updateBatchById(evalIndicatorList);
	}

	@Override
	public IPage<EvalIndicator> page(EvalListQuery listQuery) {
		Page page = listQuery.toPageParam();

		return evalIndicatorMapper.listEvalIndicator(page, listQuery);
	}

	@Override
	public Integer selectIndexUsedNum(List<String> ids) {
		return evalIndicatorMapper.selectIndexUsedNum(ids);
	}

}
