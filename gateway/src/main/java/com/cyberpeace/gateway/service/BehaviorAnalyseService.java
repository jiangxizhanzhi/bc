package com.cyberpeace.gateway.service;

import com.cyberpeace.cs.service.context.behavior.BehaviorAnalyseResult;

/**
 * 行为分析Service
 *
 * @author SN
 * @date 2024/11/14
 */
public interface BehaviorAnalyseService {
    /**
     * 获取分析结果
     *
     * @return {@code BehaviorAnalyseResult }
     */
    BehaviorAnalyseResult getAnalyseResult();

}
