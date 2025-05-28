/**
 * 这段代码定义了一个名为 `EvalIndicatorService` 的接口，该接口继承自 `IService<EvalIndicator>`。这个接口主要用于处理与评估指标相关的业务逻辑。以下是对该接口的简要分析： 
 * ### 接口概述 
 * - **包名**：`com.cyberpeace.cr.evaluation.service` 
 * - **接口名称**：`EvalIndicatorService` 
 * - **实现类**：需要开发者自己实现该接口 
 * ### 方法描述 
 * 1. **saveBatchContext** 
 * - **功能**：批量保存指标项。 
 * - **参数**： 
 * - `EvalIndicatorReq evalIndicators`：包含指标信息的请求对象。 
 * - `String userId`：用户ID。 
 * - `List<String> collectionIds`：收集ID列表。 
 * - **返回值**：无。 
 * 2. **batchDelete** 
 * - **功能**：批量删除指标项。 
 * - **参数**：`List<String> ids`：指标项ID列表。 
 * - **返回值**：`Boolean`，表示删除操作是否成功。 
 * 3. **deleteAggregationAndCollection** 
 * - **功能**：删除指定指标项的聚合项和采集项。 
 * - **参数**：`List<String> evalIndicatorIds`：评估指标ID列表。 
 * - **返回值**：无。 
 * 4. **updateBatchContext** 
 * - **功能**：批量更新指标项。 
 * - **参数**： 
 * - `EvalIndicatorReq evalIndicatorReq`：包含更新信息的请求对象。 
 * - `List<EvalIndicatorContext> evalIndicators`：指标上下文列表。 
 * - `String userId`：用户ID。 
 * - `List<String> collectionIds`：收集ID列表。 
 * - **返回值**：无。 
 * 5. **page** 
 * - **功能**：分页查询指标项。 
 * - **参数**：`EvalListQuery listQuery`：分页查询条件。 
 * - **返回值**：`IPage<EvalIndicator>`，分页结果。 
 * 6. **selectIndexUsedNum** 
 * - **功能**：查询已使用的指标项数量。 
 * - **参数**：`List<String> ids`：指标项ID列表。 
 * - **返回值**：`Integer`，已使用的指标项数量。 
 * ### 总结 
 * - **主要功能**：该接口提供了对评估指标的增删改查功能，并支持分页查询和统计已使用的指标项数量。 
 * - **使用场景**：适用于需要对评估指标进行管理的应用场景，如评估系统、绩效管理系统等。 
 * - **扩展性**：由于该接口是基于 `IService` 的扩展，可以利用 `MyBatis-Plus` 提供的功能进行进一步的数据库操作优化。 
 * 通过上述分析，可以看出该接口设计较为全面，覆盖了评估指标管理的主要需求。 
**/
package com.cyberpeace.cr.evaluation.service;

import java.util.List;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.controller.req.EvalIndicatorReq;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndicator;
import com.cyberpeace.cr.evaluation.service.context.EvalIndicatorContext;
import com.cyberpeace.cr.evaluation.service.context.EvalListQuery;

/**
 * 评估指示器Service
 *
 * @author SN
 * @date 2024/11/08
 */
public interface EvalIndicatorService extends IService<EvalIndicator> {

	/**
	 * 批量保存指标项
	 *
	 * @param evalIndicators
	 * @param userId
	 * @param collectionIds  收集id列表
	 */
	void saveBatchContext(EvalIndicatorReq evalIndicators, String userId,
			List<String> collectionIds);

	/**
	 * 批量删除指标项
	 *
	 * @param ids
	 * @return
	 */
	Boolean batchDelete(List<String> ids);

	/**
	 * 删除指定指标项的聚合项和采集项
	 *
	 * @param evalIndicatorIds 评估指示器id列表
	 */
	void deleteAggregationAndCollection(List<String> evalIndicatorIds);

	/**
	 * 批量更新指标项
	 *
	 * @param evalIndicatorReq
	 * @param evalIndicators
	 * @param userId
	 * @param collectionIds    收集id列表
	 */
	void updateBatchContext(EvalIndicatorReq evalIndicatorReq,
			List<EvalIndicatorContext> evalIndicators, String userId, List<String> collectionIds);

	/**
	 * 分页查询指标项
	 *
	 * @param listQuery
	 * @return
	 */
	IPage<EvalIndicator> page(EvalListQuery listQuery);

	/**
	 * 查询已使用的指标项数量
	 *
	 * @param ids 指标项id列表
	 * @return
	 */
	Integer selectIndexUsedNum(List<String> ids);
}
