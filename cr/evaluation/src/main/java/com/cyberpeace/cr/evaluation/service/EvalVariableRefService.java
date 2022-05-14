package com.cyberpeace.cr.evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAlgorithmRef;
import com.cyberpeace.cr.evaluation.dao.entity.EvalRecord;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;
import com.cyberpeace.cr.evaluation.dao.entity.EvalVariableRef;

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
}
