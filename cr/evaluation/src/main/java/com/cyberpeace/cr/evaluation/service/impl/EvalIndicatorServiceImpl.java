/**
 * 这段代码定义了一个名为 `EvalIndicatorServiceImpl` 的服务类，实现了 `EvalIndicatorService` 接口。该类主要用于处理与评估指标相关的业务逻辑。以下是代码的主要功能和结构分析： 
 * ### 主要功能 
 * 1. **保存批量上下文** (`saveBatchContext` 方法)： 
 * - 将传入的 `EvalIndicatorReq` 请求体中的评估指标列表转换为 `EvalIndicator` 对象，并调用 `saveBatch` 方法批量保存这些对象。 
 * 2. **转换评估指示器请求体至评估指示器** (`convertEvalIndicatorReqToEvalIndicator` 方法)： 
 * - 将 `EvalIndicatorContext` 对象转换为 `EvalIndicator` 对象，并设置相关属性，如用户ID、描述、数据类型等。 
 * - 特别处理了 `attribute` 属性为 `COST` 或 `BENEFIT` 的情况，将 `attributeValue` 设置为 `null`。 
 * 3. **批量删除** (`batchDelete` 方法)： 
 * - 使用 `TransactionTemplate` 开启事务，调用 `deleteAggregationAndCollection` 方法删除关联的聚合和收集记录，然后删除评估指标记录。 
 * 4. **删除聚合和收集** (`deleteAggregationAndCollection` 方法)： 
 * - 根据传入的评估指标ID列表查询并删除对应的聚合和收集记录。 
 * 5. **更新批量上下文** (`updateBatchContext` 方法)： 
 * - 将传入的 `EvalIndicatorContext` 对象列表转换为 `EvalIndicator` 对象，并调用 `updateBatchById` 方法批量更新这些对象。 
 * 6. **分页查询** (`page` 方法)： 
 * - 将传入的查询参数转换为分页参数，并调用 `evalIndicatorMapper.listEvalIndicator` 方法进行分页查询。 
 * 7. **查询指数使用数量** (`selectIndexUsedNum` 方法)： 
 * - 根据传入的ID列表查询每个ID在数据库中被使用的次数。 
 * ### 类结构 
 * - **依赖注入**：通过 `@Autowired` 注解注入了 `TransactionTemplate`、`EvalIndicatorMapper`、`EvalAggregationMapper` 和 `EvalCollectionMapper` 等依赖。 
 * - **继承关系**：该类继承了 `ServiceImpl<EvalIndicatorMapper, EvalIndicator>`，实现了 `EvalIndicatorService` 接口。 
 * ### 总结 
 * `EvalIndicatorServiceImpl` 类主要负责评估指标的增删改查操作，包括批量保存、批量删除、批量更新以及分页查询等功能。通过依赖注入和事务管理，确保了操作的一致性和可靠性。 
**/
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
 * 评估指示器Service实现
 *
 * @author SN
 * @date 2024/11/08
 */
@Service
public class EvalIndicatorServiceImpl extends ServiceImpl<EvalIndicatorMapper, EvalIndicator> implements EvalIndicatorService {

	/**
	 * 事务模板
	 */
	@Autowired
	private TransactionTemplate transactionTemplate;

	/**
	 * 评估指示器Mapper
	 */
	@Autowired
	private EvalIndicatorMapper evalIndicatorMapper;

	/**
	 * 评估集合体Mapper
	 */
	@Autowired
	private EvalAggregationMapper evalAggregationMapper;

	/**
	 * 评估收集Mapper
	 */
	@Autowired
	private EvalCollectionMapper evalCollectionMapper;

	/**
	 * 保存批量上下文
	 *
	 * @param evalIndicatorReq 评估指示器请求体
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
	 * 转换评估指示器请求体自评估指示器
	 *
	 * @param evalIndicatorContext EVAL 指标上下文
	 * @param evalIndicatorReq     评估指示器请求体
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
	 * @param evalIndicatorIds 评估指示器id列表
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
	 * @param evalIndicatorIds 评估指示器id列表
	 */
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

	/**
	 * 更新批量上下文
	 *
	 * @param evalIndicatorReq 评估指示器请求体
	 * @param evalIndicators   评估指示器
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
		Page page = listQuery.toPageParam();

		return evalIndicatorMapper.listEvalIndicator(page, listQuery);
	}

	/**
	 * 查询指数使用数量
	 *
	 * @param ids id列表
	 * @return {@code Integer }
	 */
	@Override
	public Integer selectIndexUsedNum(List<String> ids) {
		return evalIndicatorMapper.selectIndexUsedNum(ids);
	}

}
