package com.cyberpeace.cr.evaluation.service;


import java.io.Serializable;
import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAlgorithmRef;

/**
 * 测评算法裁判Service
 *
 * @author SN
 * @date 2024/11/14
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
