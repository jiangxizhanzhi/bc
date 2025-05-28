package com.cyberpeace.space.service;

import com.cyberpeace.space.service.context.rule.BaseDataRuleStatistics;
import com.cyberpeace.space.service.context.rule.RaceStatistics;
import com.cyberpeace.space.service.context.rule.ThreatDetectStatistics;

/**
 * 空间统治常用Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
public interface SpaceRuleCommonService {
    RaceStatistics getRaceStatistics();

    ThreatDetectStatistics getThreatDetectStatistics();

    BaseDataRuleStatistics getBaseDataStatistics();
}
