package com.cyberpeace.space.service;

import com.cyberpeace.space.service.context.rule.BaseDataRuleStatistics;
import com.cyberpeace.space.service.context.rule.RaceStatistics;
import com.cyberpeace.space.service.context.rule.ThreatDetectStatistics;

public interface SpaceRuleCommonService {
    RaceStatistics getRaceStatistics();

    ThreatDetectStatistics getThreatDetectStatistics();

    BaseDataRuleStatistics getBaseDataStatistics();
}
