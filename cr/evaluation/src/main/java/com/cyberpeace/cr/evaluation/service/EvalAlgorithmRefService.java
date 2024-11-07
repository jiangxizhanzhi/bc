package com.cyberpeace.cr.evaluation.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAlgorithmRef;

import java.io.Serializable;
import java.util.List;

/**
 * 评估算法裁判Service
 *
 * @author SN
 * @date 2024/11/07
 */
public interface EvalAlgorithmRefService extends IService<EvalAlgorithmRef> {
    /**
     * 解析算法可配置参数
     *
     * @param argumentsString 算法可配置参数JsonString
     * @return 参数列表
     */
    List<Serializable> buildOtherArguments(String argumentsString);
}
