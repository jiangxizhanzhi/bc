package com.cyberpeace.cr.evaluation.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAlgorithmRef;

import java.io.Serializable;
import java.util.List;

public interface EvalAlgorithmRefService extends IService<EvalAlgorithmRef> {
    List<Serializable> buildOtherArguments(String argumentsString);
}
