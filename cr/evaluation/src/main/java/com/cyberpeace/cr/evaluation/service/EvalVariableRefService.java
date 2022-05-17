package com.cyberpeace.cr.evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalVariableDataPageRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalVariableGraphRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAlgorithmRef;
import com.cyberpeace.cr.evaluation.dao.entity.EvalRecord;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;
import com.cyberpeace.cr.evaluation.dao.entity.EvalVariableRef;

import java.util.List;

public interface EvalVariableRefService extends IService<EvalVariableRef> {
    /**
     * 格式化算法执行
     *
     * @return 是否成功运行
     */
    Boolean startFormat(EvalVariableRef evalVariableRef, EvalAlgorithmRef evalAlgorithmRef, EvalRecord evalRecord, EvalTask evalTask);

    /**
     * 模型检测（匹配）算法执行
     *
     * @return 是否成功运行
     */
    Boolean startMatch(EvalVariableRef evalVariableRef, EvalAlgorithmRef evalAlgorithmRef, EvalRecord evalRecord, EvalTask evalTask);

    /**
     * 评分算法执行
     *
     * @return 是否成功运行
     */
    Boolean startMark(EvalVariableRef evalVariableRef, EvalAlgorithmRef evalAlgorithmRef, EvalRecord evalRecord, EvalTask evalTask);

    EvalVariableGraphRsp graph(String variableId);

    ListRsp<EvalVariableDataPageRsp> originData(String variableId);
}
