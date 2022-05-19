package com.cyberpeace.cr.evaluation.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalRoomPairRsp;
import com.cyberpeace.cr.evaluation.dao.entity.*;

import java.util.List;
import java.util.Map;

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
     * 获取三级指标项下所有变量项`id`, `name`
     * @param indexId 三级指标项ID
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
}
