package com.cyberpeace.gateway.service;

import com.cyberpeace.cs.service.context.behavior.BehaviorAnalyseResult;

/**
 * 行为分析Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @since 1.0.0
 */
public interface BehaviorAnalyseService {
    /**
     * 获取分析结果
     *
     * @return {@code BehaviorAnalyseResult }
     * @throws
     * @see BehaviorAnalyseResult
     */
    BehaviorAnalyseResult getAnalyseResult();

}
