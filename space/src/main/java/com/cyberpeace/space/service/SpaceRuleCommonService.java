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
    /**
     * 获取比赛统计学
     *
     * @return 比赛统计数据
     * @throws
     * @see RaceStatistics
     */
    RaceStatistics getRaceStatistics();

    /**
     * 获取威胁检测统计学
     *
     * @return 威胁检测统计信息
     * @throws
     * @see ThreatDetectStatistics
     */
    ThreatDetectStatistics getThreatDetectStatistics();

    /**
     * 获取基础数据统计学
     *
     * @return 基本数据规则统计信息
     * @throws
     * @see BaseDataRuleStatistics
     */
    BaseDataRuleStatistics getBaseDataStatistics();
}
