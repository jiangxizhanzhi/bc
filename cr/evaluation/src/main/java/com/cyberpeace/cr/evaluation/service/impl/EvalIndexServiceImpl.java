/**
 * 这段代码定义了一个名为 `EvalIndexServiceImpl` 的 Spring 服务类，实现了 `EvalIndexService` 接口。这个服务类主要用于处理与评估指数（EvalIndex）相关的业务逻辑。下面是对该代码的详细分析： 
 * ### 类定义 
 * - **类名**：`EvalIndexServiceImpl` 
 * - **继承关系**：继承自 `ServiceImpl<EvalIndexMapper, EvalIndex>`，这是 MyBatis-Plus 提供的一个基类，用于简化数据访问层的操作。 
 * - **注解**： 
 * - `@Service`：将该类标识为一个 Spring 服务组件，使其可以被自动扫描和管理。 
 * ### 成员变量 
 * - **`evalIndexMapper`**：类型为 `EvalIndexMapper`，用于操作数据库中的 `EvalIndex` 表。通过 `@Autowired` 注解自动注入。 
 * ### 方法 
 * #### `getIndexesByEvalSystemId` 
 * - **方法签名**： 
 * ```java 
 * @Override 
 * public List<IndexContext> getIndexesByEvalSystemId(String systemId, Integer level) 
 * ``` 
 * - **功能**：根据给定的系统ID和水平（level），查询并转换评估指数列表为 `IndexContext` 对象列表。 
 * - **步骤**： 
 * 1. 调用 `evalIndexMapper.selectListBySystemIdAndLevel` 方法从数据库中获取指定条件的 `EvalIndex` 列表。 
 * 2. 使用 Java Stream API 将 `EvalIndex` 列表转换为 `IndexContext` 列表。 
 * 3. 在转换过程中，使用 `BeanUtils.copy` 方法将 `EvalIndex` 对象复制到 `IndexContext` 对象，并设置一些额外的属性（如 `indexId`, `childrenIds`, `algorithmId`, `params`）。 
 * #### `deleteBatchByEvalSystemId` 
 * - **方法签名**： 
 * ```java 
 * @Override 
 * public void deleteBatchByEvalSystemId(String evalSystemId) 
 * ``` 
 * - **功能**：根据给定的评估系统ID删除一批 `EvalIndex` 记录。 
 * - **步骤**： 
 * 1. 调用 `evalIndexMapper.deleteBatchByEvalSystemId` 方法执行批量删除操作。 
 * ### 总结 
 * - **主要功能**：该服务类提供了根据系统ID和水平获取评估指数列表以及根据系统ID删除评估指数记录的功能。 
 * - **技术点**： 
 * - 使用了 MyBatis-Plus 提供的 `ServiceImpl` 基类简化了数据访问层的操作。 
 * - 使用了 Java Stream API 和 `BeanUtils.copy` 方法进行对象的映射和转换。 
 * - 使用了 MyBatis 提供的 Mapper 接口进行数据库操作。 
 * 这段代码展示了如何在 Spring Boot 项目中结合 MyBatis-Plus 和 Java Stream API 实现数据的查询和转换。 
**/
package com.cyberpeace.cr.evaluation.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndex;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalIndexMapper;
import com.cyberpeace.cr.evaluation.service.EvalIndexService;
import com.cyberpeace.cr.evaluation.service.context.IndexContext;

/**
 * 评估指数Service实现
 *
 * @author SN
 * @date 2024/11/08
 */
@Service
public class EvalIndexServiceImpl extends ServiceImpl<EvalIndexMapper, EvalIndex> implements EvalIndexService {

    /**
     * 评估指数Mapper
     */
    @Autowired
	private EvalIndexMapper evalIndexMapper;

    /**
     * 获取指标由评估系统id
     *
     * @param systemId 系统id
     * @param level    水平
     * @return {@code List<IndexContext> }
     */
    @Override
	public List<IndexContext> getIndexesByEvalSystemId(String systemId, Integer level) {

		List<EvalIndex> evalIndexList = evalIndexMapper.selectListBySystemIdAndLevel(systemId,
				level);

		List<IndexContext> indexContextList = evalIndexList.stream().map(evalIndex -> {
			IndexContext indexContext = BeanUtils.copy(evalIndex, IndexContext.class);

			indexContext.setIndexId(evalIndex.getResourceId());
			indexContext.setChildrenIds(
					JsonUtil.toJsonObjectArray(evalIndex.getChildrenIds(), String.class));
			indexContext.setAlgorithmId(evalIndex.getAggregatingAlgorithmId());
			indexContext.setParams(evalIndex.getAggregatingAlgorithmParams());

			return indexContext;
		}).collect(Collectors.toList());

		return indexContextList;
	}

    /**
     * 删除批量由评估系统id
     *
     * @param evalSystemId 评估系统id
     */
    @Override
	public void deleteBatchByEvalSystemId(String evalSystemId) {
		evalIndexMapper.deleteBatchByEvalSystemId(evalSystemId);
	}
}
