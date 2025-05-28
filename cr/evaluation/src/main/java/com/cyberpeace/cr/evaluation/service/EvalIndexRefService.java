/**
 * 这段代码定义了一个名为 `EvalIndexRefService` 的接口，该接口继承自 `IService<EvalIndexRef>`。这个接口主要用于评估指数裁判相关的业务逻辑操作。以下是对其主要方法的简要分析： 
 * 1. **startIndex** 
 * - **描述**: 这个方法用于开始计算某个指标项。 
 * - **参数**: 
 * - `evalIndexRef`: 当前计算的指标项对象。 
 * - `evalAlgorithmRef`: 与当前指标项关联的算法对象。 
 * - `evalIndexRefMap`: 所有指标项的映射表。 
 * - `evalVariableRefMap`: 所有变量项的映射表。 
 * - `evalRecord`: 评估记录对象。 
 * - **返回值**: 无 
 * 2. **selectVariables** 
 * - **描述**: 获取某个一级指标项下的所有变量项的 ID 和名称。 
 * - **参数**: 
 * - `indexId`: 指标项或变量项的 ID。 
 * - **返回值**: 
 * - `List<EvalRoomPairRsp>`: 包含变量项 ID 和名称的列表。 
 * 3. **thirdSpecialist** 
 * - **描述**: 根据专家评分设置生成三级指标的专家评分分值。 
 * - **参数**: 
 * - `datatype`: 专家评分设置类型。 
 * - `attribute`: 专家评分设置属性。 
 * - `attributeValue`: 中心值。 
 * - `value`: 专家评分设置值。 
 * - **返回值**: 
 * - `Integer`: 生成的专家评分分值。 
 * 4. **systemEvalVariableIds** 
 * - **描述**: 获取某个评估记录中所有被系统使用的变量项 ID。 
 * - **参数**: 
 * - `recordId`: 评估记录 ID。 
 * - **返回值**: 
 * - `Set<String>`: 不重复的变量项 ID 集合。 
 * 5. **resultIndex** 
 * - **描述**: 获取某个指标项及其子级的最新等级关联关系。 
 * - **参数**: 
 * - `indexId`: 指标项 ID。 
 * - **返回值**: 
 * - `EvalRecordResultIndexRsp`: 包含指标项及其子级的最新等级关联信息。 
 * ### 总结 
 * - **功能**: 该接口提供了评估指数裁判所需的各种核心业务操作，包括指标项计算、获取变量项信息、生成专家评分分值、获取系统使用的变量项 ID 以及获取指标项的等级关联关系等。 
 * - **使用场景**: 主要应用于评估系统的后台逻辑处理，支持评估过程中的各项计算和数据检索操作。 
 * - **依赖**: 接口依赖于多个实体类（如 `EvalIndexRef`, `EvalAlgorithmRef`, `EvalRecord` 等）和响应类（如 `EvalRoomPairRsp`, `EvalRecordResultIndexRsp`）。 
**/
package com.cyberpeace.cr.evaluation.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRecordResultIndexRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRoomPairRsp;
import com.cyberpeace.cr.evaluation.dao.entity.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 评估指数裁判Service
 *
 * @author SN
 * @date 2024/11/08
 */
public interface EvalIndexRefService extends IService<EvalIndexRef> {
    /**
     * 指标项计算
     *
     * @param evalIndexRef       指标项对象
     * @param evalAlgorithmRef   指标项关联算法
     * @param evalIndexRefMap    评估全量指标项Map
     * @param evalVariableRefMap 评估全量变量项Map
     * @param evalRecord         评估对象
     */
    void startIndex(EvalIndexRef evalIndexRef,
                       EvalAlgorithmRef evalAlgorithmRef,
                       Map<String, EvalIndexRef> evalIndexRefMap,
                       Map<String, EvalVariableRef> evalVariableRefMap,
                       EvalRecord evalRecord);


    /**
     * 获取某一级指标项下所有变量项`id`, `name`
     *
     * @param indexId 指标项/变量项ID. # 为了兼容前端调用方式, 变量项ID也接受进行处理
     * @return 变量项ID、名称列表
     */
    List<EvalRoomPairRsp> selectVariables(String indexId);

    /**
     * 三级指标专家评分分值生成
     *
     * @param datatype       专家评分设置类型
     * @param attribute      专家评分设置属性
     * @param attributeValue 中心值
     * @param value          专家评分设置值
     * @return 专家评分分值
     */
    Integer thirdSpecialist(Integer datatype, Integer attribute, Integer attributeValue, Integer value);

    /**
     * 获取评估下所有系统评估使用的变量项id
     *
     * @param recordId 评估id
     * @return 去重后的变量项ID列表
     */
    Set<String> systemEvalVariableIds(String recordId);

    /**
     * 指标项目最近等级关联关系
     *
     * @param indexId 指标项id
     * @return 指标项与其子级
     */
    EvalRecordResultIndexRsp resultIndex(String indexId);
}
