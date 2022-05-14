package com.cyberpeace.cr.evaluation.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.dao.entity.*;

import java.util.Map;

public interface EvalIndexRefService extends IService<EvalIndexRef> {
    Boolean startIndex(EvalIndexRef evalIndexRef, EvalAlgorithmRef evalAlgorithmRef, Map<String, EvalIndexRef> evalIndexRefMap, Map<String, EvalVariableRef> evalVariableRefMap);
}
