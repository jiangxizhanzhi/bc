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
     * @param evalVariableRef
     * @param evalAlgorithmRefMap
     * @param evalRecord
     * @param evalTask
     */
    String startVariableAlgorithm(EvalVariableRef evalVariableRef,
                                  Map<String, EvalAlgorithmRef> evalAlgorithmRefMap,
                                  EvalRecord evalRecord,
                                  EvalTask evalTask);

    EvalVariableGraphRsp graph(String variableId);

    ListRsp<EvalVariableDataPageRsp> originData(Page page, String variableId);

    /**
     * 评估结果变量项信息
     * @param variableId 变量项id
     * @return
     */
    EvalRecordResultVariableRsp resultVariable(String variableId);
}
