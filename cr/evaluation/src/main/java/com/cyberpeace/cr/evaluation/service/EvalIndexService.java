/**
 * 这段代码定义了一个名为 `EvalIndexService` 的接口，该接口继承自 `IService<EvalIndex>`。这个接口是用于管理评估指数（`EvalIndex`）的业务逻辑。以下是对代码的简要分析： 
 * ### 主要组成部分 
 * 1. **接口声明**: 
 * ```java 
 * public interface EvalIndexService extends IService<EvalIndex> 
 * ``` 
 * - `EvalIndexService` 接口继承了 `IService<EvalIndex>`，这表明它是一个扩展了 MyBatis-Plus 框架的服务接口。 
 * 2. **方法定义**: 
 * - `getIndexesByEvalSystemId` 方法： 
 * ```java 
 * List<IndexContext> getIndexesByEvalSystemId(String systemId, Integer level); 
 * ``` 
 * - 该方法接收两个参数：`systemId` 和 `level`，返回一个 `IndexContext` 对象的列表。 
 * - 用途：根据体系ID和层级获取指标项列表。 
 * - `deleteBatchByEvalSystemId` 方法： 
 * ```java 
 * void deleteBatchByEvalSystemId(String evalSystemId); 
 * ``` 
 * - 该方法接收一个 `evalSystemId` 参数，没有返回值。 
 * - 用途：根据评估系统ID批量删除指标项。 
 * ### 注释 
 * - 接口上有类级别的注释，说明了该接口的作用。 
 * - 方法上有详细的注释，描述了方法的功能、参数及返回值。 
 * ### 总结 
 * - `EvalIndexService` 是一个用于管理评估指数的接口。 
 * - 它包含两个主要方法：获取指标项列表和批量删除指标项。 
 * - 这个接口可以方便地与数据层交互，实现对评估指数的增删改查操作。 
 * 这些方法的具体实现需要在具体的实现类中完成。 
**/
package com.cyberpeace.cr.evaluation.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndex;
import com.cyberpeace.cr.evaluation.service.context.IndexContext;

/**
 * 评估指数Service
 *
 * @author SN
 * @date 2024/11/08
 */
public interface EvalIndexService extends IService<EvalIndex> {

    /**
     * 通过体系id获取指标项列表
     *
     * @param systemId
     * @param level
     * @return
     */
    List<IndexContext> getIndexesByEvalSystemId(String systemId, Integer level);

    /**
     * 删除体系id的指标项
     *
     * @param evalSystemId 评估系统id
     */
    void deleteBatchByEvalSystemId(String evalSystemId);
}
