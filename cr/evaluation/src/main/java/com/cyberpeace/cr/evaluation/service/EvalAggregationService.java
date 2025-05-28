/**
 * 这段代码定义了一个名为 `EvalAggregationService` 的接口，该接口扩展了 `IService<EvalAggregation>` 接口。这个接口主要用于处理与 `EvalAggregation` 实体相关的业务逻辑。以下是代码的详细分析： 
 * ### 1. 包声明和导入 
 * ```java 
 * package com.cyberpeace.cr.evaluation.service; 
 * import java.util.List; 
 * import com.baomidou.mybatisplus.extension.service.IService; 
 * import com.cyberpeace.cr.evaluation.dao.entity.EvalAggregation; 
 * import com.cyberpeace.cr.evaluation.service.context.EvalAggregationContext; 
 * ``` 
 * - **包声明**：代码位于 `com.cyberpeace.cr.evaluation.service` 包下。 
 * - **导入**： 
 * - `java.util.List`：用于表示列表。 
 * - `com.baomidou.mybatisplus.extension.service.IService`：一个通用的服务接口，通常用于 MyBatis-Plus 框架中。 
 * - `com.cyberpeace.cr.evaluation.dao.entity.EvalAggregation`：实体类，表示评估聚合项。 
 * - `com.cyberpeace.cr.evaluation.service.context.EvalAggregationContext`：上下文对象，可能包含评估聚合项的额外信息。 
 * ### 2. 接口定义 
 * ```java 
 * /** 
 * * 评估集合体Service 
 * * 
 * * @author SN 
 * * @date 2024/11/08 
 * *
 * public interface EvalAggregationService extends IService<EvalAggregation> { 
 * ``` 
 * - **注释**：接口的文档说明，描述了该接口的作用是“评估集合体Service”。 
 * - **继承**：`EvalAggregationService` 继承了 `IService<EvalAggregation>` 接口，这通常意味着该接口提供了基本的 CRUD 操作（创建、读取、更新、删除）。 
 * ### 3. 方法定义 
 * ```java 
 * /** 
 * * 批量保存评估聚合项 
 * * 
 * * @param evalAggregations 评估聚合项列表 
 * *
 * void saveBatchContext(List<EvalAggregationContext> evalAggregations); 
 * ``` 
 * - **方法**：`saveBatchContext` 
 * - **参数**：`List<EvalAggregationContext> evalAggregations`，表示需要批量保存的评估聚合项列表。 
 * - **功能**：批量保存评估聚合项，这些项可能包含一些额外的上下文信息。 
 * ```java 
 * /** 
 * * 通过指标项id获取评估聚合项 
 * * 
 * * @param resourceId 指标项id 
 * * @return 
 * *
 * List<EvalAggregationContext> getContextByEvalIndicatorId(String resourceId); 
 * ``` 
 * - **方法**：`getContextByEvalIndicatorId` 
 * - **参数**：`String resourceId`，表示指标项的 ID。 
 * - **功能**：根据指标项 ID 获取相应的评估聚合项列表，并返回这些项的上下文信息。 
 * ### 总结 
 * - `EvalAggregationService` 是一个接口，用于处理与 `EvalAggregation` 实体相关的业务逻辑。 
 * - 它继承了 `IService<EvalAggregation>` 接口，提供了一些基础的 CRUD 操作。 
 * - 接口定义了两个主要的方法： 
 * - `saveBatchContext`：批量保存评估聚合项。 
 * - `getContextByEvalIndicatorId`：根据指标项 ID 获取评估聚合项的上下文信息。 
 * 这个接口的设计目的是为了更好地管理和操作评估聚合项的数据。 
**/
package com.cyberpeace.cr.evaluation.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAggregation;
import com.cyberpeace.cr.evaluation.service.context.EvalAggregationContext;

/**
 * 评估集合体Service
 *
 * @author SN
 * @date 2024/11/08
 */
public interface EvalAggregationService extends IService<EvalAggregation> {

    /**
     * 批量保存评估聚合项
     *
     * @param evalAggregations 评估聚合项列表
     */
    void saveBatchContext(List<EvalAggregationContext> evalAggregations);

    /**
     * 通过指标项id获取评估聚合项
     *
     * @param resourceId 指标项id
     * @return
     */
    List<EvalAggregationContext> getContextByEvalIndicatorId(String resourceId);

}
