package com.cyberpeace.cr.evaluation.service;

import java.util.Map;

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

public interface EvalVariableRefService extends IService<EvalVariableRef> {

    /**
     * 采集变量项目算法执行
     * @param evalVariableRef 变量项对象
     * @param evalAlgorithmRefMap 评估使用算法Map
     * @param evalRecord 评估对象
     * @param evalTask 测评实例对象
     */
    String startVariableAlgorithm(EvalVariableRef evalVariableRef,
                                  Map<String, EvalAlgorithmRef> evalAlgorithmRefMap,
                                  EvalRecord evalRecord,
                                  EvalTask evalTask);

    /**
     * 获取变量项结果折线图
     * @param variableId 变量项id
     * @return 变量项目折线图信息
     */
    EvalVariableGraphRsp graph(String variableId);

    /**
     * 获取变量项探针原始数据
     * @param page 分页信息
     * @param variableId 变量项id
     * @return 探针原始数据分页信息
     */
    ListRsp<EvalVariableDataPageRsp> originData(Page<EvalVariableDataPageRsp> page, String variableId);

    /**
     * 获取评估结果变量项信息
     * @param variableId 变量项id
     * @return 评估结果变量项信息
     */
    EvalRecordResultVariableRsp resultVariable(String variableId);
}
