package com.cyberpeace.cr.evaluation.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.dao.entity.EvalAlgorithmRef;

import java.io.Serializable;
import java.util.List;

/**
 * 评估算法裁判Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see IService
 * @since 1.0.0
 */
public interface EvalAlgorithmRefService extends IService<EvalAlgorithmRef> {
    /**
     * 解析算法可配置参数
     *
     * @param argumentsString 算法可配置参数JsonString
     * @return 参数列表
     * @throws
     * @see String
     * @see List<Serializable>
     */
    List<Serializable> buildOtherArguments(String argumentsString);
}
