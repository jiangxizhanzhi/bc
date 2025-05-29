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
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalAggregationServiceImpl` 的服务实现类，用于处理与 `EvalAggregation` 实体相关的业务逻辑。以下是对该代码的简要分析： 
 * ### 类概述 
 * - **包名**: `com.cyberpeace.cr.evaluation.service.impl` 
 * - **类名**: `EvalAggregationServiceImpl` 
 * - **继承关系**: 继承自 `ServiceImpl<EvalAggregationMapper, EvalAggregation>`，实现了 `EvalAggregationService` 接口。 
 * ### 类成员变量 
 * - **evalAggregationMapper**: 通过 `@Autowired` 注入的 `EvalAggregationMapper` 对象，用于操作数据库中的 `EvalAggregation` 表。 
 * ### 方法概述 
 * #### 1. `saveBatchContext` 
 * - **功能**: 批量保存 `EvalAggregationContext` 到数据库。 
 * - **参数**: `List<EvalAggregationContext> evalAggregations` - 需要保存的 `EvalAggregationContext` 列表。 
 * - **过程**: 
 * - 将 `EvalAggregationContext` 转换为 `EvalAggregation`。 
 * - 设置 `EvalAggregation` 中的 `resourceId` 和 `collectionIds`。 
 * - 使用 `this.saveBatch(evalAggregationList)` 方法批量保存到数据库。 
 * #### 2. `getContextByEvalIndicatorId` 
 * - **功能**: 根据 `evalIndicatorId` 获取对应的 `EvalAggregationContext` 列表。 
 * - **参数**: `String evalIndicatorId` - 评估指示器 ID。 
 * - **过程**: 
 * - 从数据库中查询对应的 `EvalAggregation` 列表。 
 * - 将 `EvalAggregation` 转换为 `EvalAggregationContext`。 
 * - 设置 `EvalAggregationContext` 中的 `evalAggregationId` 和 `aggregationIds`。 
 * - 返回转换后的 `EvalAggregationContext` 列表。 
 * ### 工具类 
 * - **BeanUtils**: 用于对象之间的属性复制。 
 * - **JsonUtil**: 用于 JSON 字符串和对象之间的转换。 
 * - **Lists.newArrayList**: 用于创建一个不可变的列表。 
 * ### 总结 
 * 这个类主要负责 `EvalAggregation` 和 `EvalAggregationContext` 之间的转换，并提供了批量保存和根据评估指示器 ID 查询相关数据的功能。使用了 MyBatis-Plus 提供的 `ServiceImpl` 基础实现，简化了数据库操作。 
**/
@Service
public class EvalAggregationServiceImpl extends ServiceImpl<EvalAggregationMapper, EvalAggregation> implements EvalAggregationService {

	/**
	 * 评估集合体Mapper
	 */
	@Autowired
	EvalAggregationMapper evalAggregationMapper;

	/**
	 * 保存批量上下文
	 *
	 * @param evalAggregations eval 聚合
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

