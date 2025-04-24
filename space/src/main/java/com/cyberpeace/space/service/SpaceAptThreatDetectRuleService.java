
package com.cyberpeace.space.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.space.dao.entity.SpaceAptThreatDetectRule;
import com.cyberpeace.space.service.context.index.KnowledgeChart;

public interface SpaceAptThreatDetectRuleService extends IService<SpaceAptThreatDetectRule>

{
    KnowledgeChart getRuleAptThreatChart();
}