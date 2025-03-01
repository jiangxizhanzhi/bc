package com.cyberpeace.cr.evaluation.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.StreamApiUtils;
import com.cyberpeace.cr.evaluation.controller.req.EvalIndicatorReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalIndicatorRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAggregation;
import com.cyberpeace.cr.evaluation.dao.entity.EvalCollection;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndexRef;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndicator;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalAggregationMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalCollectionMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalIndicatorMapper;
import com.cyberpeace.cr.evaluation.service.EvalAggregationService;
import com.cyberpeace.cr.evaluation.service.EvalCollectionService;
import com.cyberpeace.cr.evaluation.service.EvalIndicatorService;
import com.cyberpeace.cr.evaluation.service.context.EvalIndicatorContext;
import com.cyberpeace.cr.evaluation.service.context.EvalListQuery;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.stream.Collectors;

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

	@Autowired
	private EvalCollectionService evalCollectionService;

	@Autowired
	private EvalAggregationService evalAggregationService;

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
		evalIndicator.setMatrixData(evalIndicators.get(0).getMatrixData());

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
				EvalAggregation::getResourceId);
		List<String> evalCollectionIds = StreamApiUtils.map(
				evalCollectionMapper.selectIdsByEvalIndicatorIds(evalIndicatorIds),
				EvalCollection::getResourceId);

		if (!evalAggregationIds.isEmpty()) {
			evalAggregationMapper.deleteBatchIds(evalAggregationIds);
		}
		if (!evalCollectionIds.isEmpty()) {
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
		Page<EvalIndicator> page = listQuery.toPageParam();

		return evalIndicatorMapper.listEvalIndicator(page, listQuery);
	}

	@Override
	public Integer selectIndexUsedNum(List<String> ids) {
		return evalIndicatorMapper.selectIndexUsedNum(ids);
	}

	@Override
	public EvalIndicatorRsp convertEvalIndicatorToEvalSystemRsp(EvalIndicator evalIndicator) {

		EvalIndicatorRsp evalSystemRsp = BeanUtils.copy(evalIndicator, EvalIndicatorRsp.class);

		EvalIndicatorContext evalIndicatorContext = BeanUtils.copy(evalIndicator,
				EvalIndicatorContext.class);
		evalIndicatorContext.setAggregationIds(
				JsonUtil.toJsonObjectArray(evalIndicator.getAggregationIds(), String.class));
		evalIndicatorContext.setEvalIndicatorId(evalIndicator.getResourceId());

		evalSystemRsp.setEvalIndicatorId(evalIndicator.getResourceId());
		evalSystemRsp.setEvalCollections(
				evalCollectionService.getContextByEvalIndicatorId(evalIndicator.getResourceId()));
		evalSystemRsp.setEvalAggregations(
				evalAggregationService.getContextByEvalIndicatorId(evalIndicator.getResourceId()));
		evalSystemRsp.setEvalIndicators(Lists.newArrayList(evalIndicatorContext));

		return evalSystemRsp;
	}

}
