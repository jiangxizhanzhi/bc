package com.cyberpeace.cr.evaluation.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRecordResultIndexRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRoomPairRsp;
import com.cyberpeace.cr.evaluation.dao.entity.*;

import java.util.List;
import java.util.Map;
import java.util.Set;

public interface EvalIndexRefService extends IService<EvalIndexRef> {
    /**
     * 指标项计算
     * @return 是否执行成功
     */
    void startIndex(EvalIndexRef evalIndexRef,
                       EvalAlgorithmRef evalAlgorithmRef,
                       Map<String, EvalIndexRef> evalIndexRefMap,
                       Map<String, EvalVariableRef> evalVariableRefMap,
                       EvalRecord evalRecord);


    /**
     * 获取某一级指标项下所有变量项`id`, `name`
     * @param indexId 指标项/变量项ID. # 为了兼容前端调用方式, 变量项ID也接受进行处理
     * @return List
     */
    List<EvalRoomPairRsp> selectVariables(String indexId);

    /**
     * 三级指标专家评分分值生成
     * @param evalIndicator
     * @param value
     * @return 0~100
     */
    Integer thirdSpecialist(Integer datatype, Integer attribute, Integer attributeValue, Integer value);

    /**
     * 获取评估下所有系统评估使用的变量项id
     * @param recordId 评估id
     * @return List
     */
    Set<String> systemEvalVariableIds(String recordId);

    /**
     * 指标项目最近等级关联关系
     * @param recordId 评估任务id
     * @param indexId 指标项id
     * @return EvalRecordResultIndexRsp
     */
    EvalRecordResultIndexRsp resultIndex(String recordId, String indexId);
}
