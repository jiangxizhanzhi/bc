/**
 * 这段代码定义了一个名为 `SpaceRuleCommonService` 的接口，位于 `com.cyberpeace.space.service` 包中。以下是对其的简要分析： 
 * 1. **功能描述**： 
 * - 接口描述了与空间统治相关的通用服务逻辑。 
 * - 提供了获取不同类型统计数据的方法。 
 * 2. **方法说明**： 
 * - `getRaceStatistics()`：返回 `RaceStatistics` 对象，用于获取比赛相关统计信息。 
 * - `getThreatDetectStatistics()`：返回 `ThreatDetectStatistics` 对象，用于获取威胁检测相关的统计信息。 
 * - `getBaseDataStatistics()`：返回 `BaseDataRuleStatistics` 对象，用于获取基础数据规则相关的统计信息。 
 * 3. **注释**： 
 * - 每个方法都有详细的 Javadoc 注释，清楚地说明了方法的功能、返回值以及关联的类。 
 * 4. **用途**： 
 * - 该接口可能是为了统一管理与空间统治相关的统计学操作，便于后续扩展和维护。 
 * 总体而言，这是一个清晰且结构良好的接口设计，专注于提供统计数据的服务功能。 
**/
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
