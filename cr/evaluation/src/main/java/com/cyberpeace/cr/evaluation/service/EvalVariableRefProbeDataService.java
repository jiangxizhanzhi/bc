/**
 * 这段代码定义了一个名为 `EvalVariableRefProbeDataService` 的接口，该接口继承了 `IService<EvalVariableRefProbeData>`。这个接口提供了与评估变量裁判探针数据相关的服务方法。以下是具体的分析： 
 * ### 包和导入 
 * - **包名**：`com.cyberpeace.cr.evaluation.service` 
 * - **导入的类和接口**： 
 * - `com.baomidou.mybatisplus.extension.plugins.pagination.Page`：分页信息。 
 * - `com.baomidou.mybatisplus.extension.service.IService`：一个通用的服务接口。 
 * - `com.cyberpeace.common.model.ListRsp`：封装列表响应结果的类。 
 * - `com.cyberpeace.cr.evaluation.controller.rsp.EvalVariableDataPageRsp`：用于表示分页数据响应结果的类。 
 * - `com.cyberpeace.cr.evaluation.dao.entity.EvalVariableRefProbeData`：实体类，表示评估变量裁判探针数据。 
 * - `com.cyberpeace.cr.evaluation.service.context.EvalVariableRefProbeDataContext`：上下文类，表示评估变量裁判探针数据的上下文。 
 * ### 接口定义 
 * - **接口名称**：`EvalVariableRefProbeDataService` 
 * - **接口描述**：提供评估变量裁判探针数据的相关服务。 
 * ### 方法定义 
 * 1. **方法一：`variableProbeDataResult`** 
 * - **描述**：获取单次评估关联变量项探针数据。 
 * - **参数**：`String variableId`（变量项ID）。 
 * - **返回值**：`List<EvalVariableRefProbeDataContext>`（评估变量裁判探针数据的上下文列表）。 
 * 2. **方法二：`variableProbeData`** 
 * - **描述**：获取变量项关联探针原始数据。 
 * - **参数**：`Page page`（分页信息），`String variableId`（变量项ID）。 
 * - **返回值**：`ListRsp<EvalVariableDataPageRsp>`（封装了分页数据的响应结果）。 
 * ### 总结 
 * - 这个接口主要用于处理评估变量裁判探针数据的操作。 
 * - 它提供了两个主要的方法：一个用于获取单次评估的探针数据上下文，另一个用于获取分页的探针原始数据。 
 * - 接口的设计符合面向服务的架构思想，使得业务逻辑可以更加清晰地分离和管理。 
**/
package com.cyberpeace.cr.evaluation.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalVariableDataPageRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalVariableRefProbeData;
import com.cyberpeace.cr.evaluation.service.context.EvalVariableRefProbeDataContext;

import java.util.List;

/**
 * 评估变量裁判探针数据Service
 *
 * @author SN
 * @date 2024/11/08
 */
public interface EvalVariableRefProbeDataService extends IService<EvalVariableRefProbeData> {
    /**
     * 获取单次评估关联变量项探针数据
     *
     * @param variableId 变量项id
     * @return 单次评估关联变量项探针数据
     */
    List<EvalVariableRefProbeDataContext> variableProbeDataResult(String variableId);

    /**
     * 获取变量项关联探针原始数据
     *
     * @param page       分页信息
     * @param variableId 变量项id
     * @return 探针采集原始数据
     */
    ListRsp<EvalVariableDataPageRsp> variableProbeData(Page page, String variableId);
}
