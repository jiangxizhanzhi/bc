package com.cyberpeace.cr.evaluation.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRecordResultVariableRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalVariableDataPageRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalVariableGraphRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAlgorithmRef;
import com.cyberpeace.cr.evaluation.dao.entity.EvalRecord;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;
import com.cyberpeace.cr.evaluation.dao.entity.EvalVariableRef;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * 评估变量裁判Service
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalVariableRefService` 的接口，该接口继承自 `IService<EvalVariableRef>`。这个接口主要用于处理与评估变量相关的业务逻辑。以下是详细的分析和总结： 
 * ### 接口概述 
 * - **包名**: `com.cyberpeace.cr.evaluation.service` 
 * - **接口名称**: `EvalVariableRefService` 
 * ### 接口继承 
 * - 继承自 `IService<EvalVariableRef>`，这表明该接口可能使用了 MyBatis-Plus 框架来处理数据库操作。 
 * ### 常量定义 
 * - `BASE`: 包含一些特定的时间字段（例如 "002" 和 "003"），这些字段可能在某些查询或算法中具有特殊意义。 
 * - `ES_PROBES`: 包含一些特定的探针 ID（例如 "006" 到 "010"），这些探针的数据通过 Elasticsearch 查询，并且需要特定的方式进行分页处理。 
 * ### 方法定义 
 * 1. **`startVariableAlgorithm`** 
 * - **功能**: 执行变量项目的算法。 
 * - **参数**: 
 * - `EvalVariableRef evalVariableRef`: 变量项对象。 
 * - `Map<String, EvalAlgorithmRef> evalAlgorithmRefMap`: 评估使用的算法映射。 
 * - `EvalRecord evalRecord`: 评估记录对象。 
 * - `EvalTask evalTask`: 评估任务对象。 
 * - **返回值**: `String`，可能表示算法执行的结果或状态。 
 * 2. **`graph`** 
 * - **功能**: 获取变量项结果的折线图信息。 
 * - **参数**: 
 * - `String variableId`: 变量项的 ID。 
 * - **返回值**: `EvalVariableGraphRsp`，包含折线图的信息。 
 * 3. **`originData`** 
 * - **功能**: 获取变量项探针的原始数据。 
 * - **参数**: 
 * - `Page page`: 分页信息。 
 * - `String variableId`: 变量项的 ID。 
 * - **返回值**: `ListRsp<EvalVariableDataPageRsp>`，包含分页后的原始数据信息。 
 * 4. **`resultVariable`** 
 * - **功能**: 获取评估结果中的变量项信息。 
 * - **参数**: 
 * - `String variableId`: 变量项的 ID。 
 * - **返回值**: `EvalRecordResultVariableRsp`，包含评估结果中的变量项信息。 
 * ### 总结 
 * - `EvalVariableRefService` 接口主要负责评估变量相关的业务逻辑，包括算法执行、图表生成、数据分页和结果获取。 
 * - 使用了 MyBatis-Plus 进行数据库操作，并且部分方法涉及到与 Elasticsearch 的交互。 
 * - 接口中定义了一些常量，用于标识特定的时间字段和探针 ID，这些常量在实际业务中可能有重要的作用。 
**/
public interface EvalVariableRefService extends IService<EvalVariableRef> {

    /**
     * 时间字段获取存在差异的探针
     */
    List<String> BASE = Arrays.asList("002", "003");

    /**
     * 由es查询的探针数据, 无法使用`page`、`per_page`分页, 需要传入`requestId`、`per_page`迭代获取
     */
    List<String> ES_PROBES = Arrays.asList("006", "007", "008", "009", "010");

    /**
     * 采集变量项目算法执行
     *
     * @param evalVariableRef     变量项对象
     * @param evalAlgorithmRefMap 评估使用算法Map
     * @param evalRecord          评估对象
     * @param evalTask            测评实例对象
     * @return {@code String }
     */
    String startVariableAlgorithm(EvalVariableRef evalVariableRef,
                                  Map<String, EvalAlgorithmRef> evalAlgorithmRefMap,
                                  EvalRecord evalRecord,
                                  EvalTask evalTask);

    /**
     * 获取变量项结果折线图
     *
     * @param variableId 变量项id
     * @return 变量项目折线图信息
     */
    EvalVariableGraphRsp graph(String variableId);

    /**
     * 获取变量项探针原始数据
     *
     * @param page       分页信息
     * @param variableId 变量项id
     * @return 探针原始数据分页信息
     */
    ListRsp<EvalVariableDataPageRsp> originData(Page page, String variableId);

    /**
     * 获取评估结果变量项信息
     *
     * @param variableId 变量项id
     * @return 评估结果变量项信息
     */
    EvalRecordResultVariableRsp resultVariable(String variableId);
}

