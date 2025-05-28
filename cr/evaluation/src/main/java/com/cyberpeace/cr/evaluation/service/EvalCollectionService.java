/**
 * 这段代码定义了一个名为 `EvalCollectionService` 的接口，该接口扩展了 `IService<EvalCollection>` 接口。这个服务接口主要用于处理与 `EvalCollection` 实体相关的业务逻辑。下面是对代码的简要分析： 
 * ### 包和导入 
 * - `com.cyberpeace.cr.evaluation.service`：这是接口所在的包。 
 * - 导入了 `java.util.List` 和其他两个自定义类 `EvalCollection` 和 `EvalCollectionContext`。 
 * ### 接口定义 
 * - `EvalCollectionService` 接口继承自 `IService<EvalCollection>`，这意味着它提供了一些默认的方法来操作 `EvalCollection` 实体。 
 * - 定义了两个方法： 
 * - `saveBatchContext`：批量保存采集项。接受一个 `List<EvalCollectionContext>` 类型的参数，并返回 `void`。 
 * - `getContextByEvalIndicatorId`：通过指标项ID获取采集项列表。接受一个 `String` 类型的参数 `evalIndicatorId`，并返回一个 `List<EvalCollectionContext>`。 
 * ### 方法说明 
 * - `saveBatchContext` 方法用于批量保存采集项，通常用于将多个采集项一次性插入到数据库中。 
 * - `getContextByEvalIndicatorId` 方法用于根据给定的指标项ID获取对应的采集项列表，这有助于查询特定指标下的所有采集项。 
 * ### 总结 
 * 这段代码定义了一个服务接口 `EvalCollectionService`，它提供了两个主要功能：批量保存采集项和通过指标项ID获取采集项列表。这些方法使得处理 `EvalCollection` 实体的业务逻辑更加模块化和易于维护。 
**/
package com.cyberpeace.cr.evaluation.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.dao.entity.EvalCollection;
import com.cyberpeace.cr.evaluation.service.context.EvalCollectionContext;

/**
 * 评估收集Service
 *
 * @author SN
 * @date 2024/11/08
 */
public interface EvalCollectionService extends IService<EvalCollection> {

	/**
	 * 批量保存采集项
	 *
	 * @param evalCollectionContexts eval 集合上下文
	 */
	void saveBatchContext(List<EvalCollectionContext> evalCollectionContexts);

	/**
	 * 通过指标项id获取采集项列表
	 *
	 * @param evalIndicatorId
	 * @return
	 */
	List<EvalCollectionContext> getContextByEvalIndicatorId(String evalIndicatorId);
}
