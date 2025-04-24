
package com.cyberpeace.space.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.space.dao.entity.SpaceEventIntegrationRule;
import com.cyberpeace.space.service.context.index.KnowledgeChart;

public interface SpaceEventIntegrationRuleService extends IService<SpaceEventIntegrationRule>

{
    KnowledgeChart getRuleHostChart();
}