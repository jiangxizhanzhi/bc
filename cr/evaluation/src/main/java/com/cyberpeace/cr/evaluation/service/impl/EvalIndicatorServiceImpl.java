package com.cyberpeace.cr.evaluation.service.impl;

import java.util.List;
import java.util.stream.Collectors;

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
import com.cyberpeace.cr.evaluation.dao.entity.EvalAggregation;
import com.cyberpeace.cr.evaluation.dao.entity.EvalCollection;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndexRef;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndicator;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalAggregationMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalCollectionMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalIndicatorMapper;
import com.cyberpeace.cr.evaluation.service.EvalIndicatorService;
import com.cyberpeace.cr.evaluation.service.context.EvalIndicatorContext;
import com.cyberpeace.cr.evaluation.service.context.EvalListQuery;

/**
 * 测评指示器Service实现
 *
 * @author SN
 * @date 2024/11/14
 */
@Service
public class EvalIndicatorServiceImpl extends ServiceImpl<EvalIndicatorMapper, EvalIndicator> implements EvalIndicatorService {

	/**
	 * 事务模板
	 */
	@Autowired
	private TransactionTemplate transactionTemplate;

	/**
	 * 测评指示器Mapper
	 */
	@Autowired
	private EvalIndicatorMapper evalIndicatorMapper;

	/**
	 * 测评集合体Mapper
	 */
	@Autowired
	private EvalAggregationMapper evalAggregationMapper;

	/**
	 * 测评收集Mapper
	 */
	@Autowired
	private EvalCollectionMapper evalCollectionMapper;

	/**
	 * 保存批量上下文
	 *
	 * @param evalIndicatorReq 测评指示器请求体
	 * @param userId           用户id
	 * @param collectionIds    收集id列表
	 */
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

	/**
	 * 转换测评指示器请求体自测评指示器
	 *
	 * @param evalIndicatorContext 测评指示器上下文
	 * @param evalIndicatorReq     测评指示器请求体
	 * @param userId               用户id
	 * @param collectionIds        收集id列表
	 * @return {@code EvalIndicator }
	 */
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

	/**
	 * 批量删除
	 *
	 * @param evalIndicatorIds 测评指示器id列表
	 * @return {@code Boolean }
	 */
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

	/**
	 * 删除聚合和集合
	 *
	 * @param evalIndicatorIds 测评指示器id列表
	 */
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

	/**
	 * 更新批量上下文
	 *
	 * @param evalIndicatorReq 测评指示器请求体
	 * @param evalIndicators   测评指标
	 * @param userId           用户id
	 * @param collectionIds    收集id列表
	 */
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

	/**
	 * 分页
	 *
	 * @param listQuery 列表查询
	 * @return {@code IPage<EvalIndicator> }
	 */
	@Override
	public IPage<EvalIndicator> page(EvalListQuery listQuery) {
		Page<EvalIndicator> page = listQuery.toPageParam();

		return evalIndicatorMapper.listEvalIndicator(page, listQuery);
	}

	/**
	 * 查询编号使用数量
	 *
	 * @param ids id列表
	 * @return {@code Integer }
	 */
	@Override
	public Integer selectIndexUsedNum(List<String> ids) {
		return evalIndicatorMapper.selectIndexUsedNum(ids);
	}

}
