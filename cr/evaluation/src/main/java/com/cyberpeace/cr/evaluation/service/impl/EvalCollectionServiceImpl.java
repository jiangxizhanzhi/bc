package com.cyberpeace.cr.evaluation.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.evaluation.dao.entity.EvalCollection;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalCollectionMapper;
import com.cyberpeace.cr.evaluation.service.EvalCollectionService;
import com.cyberpeace.cr.evaluation.service.context.EvalCollectionContext;
import com.google.common.collect.Lists;

/**
 * 评估收集Service实现
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalCollectionServiceImpl` 的服务实现类，用于处理与 `EvalCollection` 实体相关的业务逻辑。以下是对该代码的简要分析： 
 * ### 类概述 
 * - **包名**: `com.cyberpeace.cr.evaluation.service.impl` 
 * - **类名**: `EvalCollectionServiceImpl` 
 * - **继承关系**: 继承自 `ServiceImpl<EvalCollectionMapper, EvalCollection>`，这是一个 MyBatis Plus 提供的基类，用于简化数据访问层的实现。 
 * ### 成员变量 
 * - **evalCollectionMapper**: 通过 `@Autowired` 注入的 `EvalCollectionMapper` 实例，用于与数据库进行交互。 
 * ### 方法 
 * #### 1. `saveBatchContext` 
 * - **描述**: 批量保存 `EvalCollectionContext` 上下文。 
 * - **参数**: 
 * - `List<EvalCollectionContext> evalCollectionContexts`: 要保存的 `EvalCollectionContext` 列表。 
 * - **实现**: 
 * - 使用 Java 8 的 Stream API 将 `EvalCollectionContext` 列表转换为 `EvalCollection` 列表。 
 * - 设置 `EvalCollection` 的 `resourceId`。 
 * - 调用父类 `ServiceImpl` 的 `saveBatch` 方法将 `EvalCollection` 列表保存到数据库中。 
 * #### 2. `getContextByEvalIndicatorId` 
 * - **描述**: 根据评估指示器 ID 获取对应的 `EvalCollectionContext` 列表。 
 * - **参数**: 
 * - `String evalIndicatorId`: 评估指示器 ID。 
 * - **实现**: 
 * - 通过 `evalCollectionMapper` 查询与指定 `evalIndicatorId` 对应的 `EvalCollection` 列表。 
 * - 使用 Java 8 的 Stream API 将 `EvalCollection` 列表转换为 `EvalCollectionContext` 列表。 
 * - 设置 `EvalCollectionContext` 的 `evalCollectionId`。 
 * - 返回转换后的 `EvalCollectionContext` 列表。 
 * ### 总结 
 * - **主要功能**: 该类实现了与 `EvalCollection` 相关的基本 CRUD 操作，包括批量保存和根据评估指示器 ID 查询上下文。 
 * - **技术点**: 使用了 MyBatis Plus 的基类简化数据访问，使用 Java 8 的 Stream API 进行集合操作，并利用 `BeanUtils` 工具类进行对象之间的转换。 
 * - **依赖**: 依赖于 `EvalCollectionMapper` 进行数据库操作，依赖于 `BeanUtils` 进行对象复制。 
 * 这段代码展示了如何在 Spring Boot 应用程序中使用 MyBatis Plus 和 Java 8 的特性来实现一个简单的业务逻辑层。 
**/
@Service
public class EvalCollectionServiceImpl extends ServiceImpl<EvalCollectionMapper, EvalCollection> implements EvalCollectionService {

	/**
	 * 评估收集Mapper
	 */
	@Autowired
	private EvalCollectionMapper evalCollectionMapper;

	/**
	 * 保存批量上下文
	 *
	 * @param evalCollectionContexts eval 集合上下文
	 */
	@Override
	public void saveBatchContext(List<EvalCollectionContext> evalCollectionContexts) {
		List<EvalCollection> evalCollectionList = evalCollectionContexts.stream()
				.map(evalCollectionContext -> {
					EvalCollection evalCollection = BeanUtils.copy(evalCollectionContext,
							EvalCollection.class);

					evalCollection.setResourceId(evalCollectionContext.getEvalCollectionId());

					return evalCollection;
				}).collect(Collectors.toList());

		this.saveBatch(evalCollectionList);
	}

	/**
	 * 获取上下文由评估指示器id
	 *
	 * @param evalIndicatorId 评估指示器id
	 * @return {@code List<EvalCollectionContext> }
	 */
	@Override
	public List<EvalCollectionContext> getContextByEvalIndicatorId(String evalIndicatorId) {
		List<EvalCollection> evalCollectionList = evalCollectionMapper
				.selectIdsByEvalIndicatorIds(Lists.newArrayList(evalIndicatorId));

		List<EvalCollectionContext> EvalCollectionContexts = evalCollectionList.stream()
				.map(evalCollection -> {
					EvalCollectionContext evalCollectionContext = BeanUtils.copy(evalCollection,
							EvalCollectionContext.class);

					evalCollectionContext.setEvalCollectionId(evalCollection.getResourceId());

					return evalCollectionContext;
				}).collect(Collectors.toList());

		return EvalCollectionContexts;
	}
}

