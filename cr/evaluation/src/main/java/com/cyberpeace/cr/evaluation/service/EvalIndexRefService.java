package com.cyberpeace.cr.evaluation.service;


import java.util.List;
import java.util.Map;
import java.util.Set;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRecordResultIndexRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRoomPairRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAlgorithmRef;
import com.cyberpeace.cr.evaluation.dao.entity.EvalIndexRef;
import com.cyberpeace.cr.evaluation.dao.entity.EvalRecord;
import com.cyberpeace.cr.evaluation.dao.entity.EvalVariableRef;

/**
 * 测评编号裁判Service
 *
 * @author SN
 * @date 2024/11/14
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
