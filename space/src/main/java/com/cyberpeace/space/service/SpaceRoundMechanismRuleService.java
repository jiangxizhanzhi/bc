
package com.cyberpeace.space.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.space.dao.entity.SpaceRoundMechanismRule;
import com.cyberpeace.space.service.context.index.KnowledgeChart;

public interface SpaceRoundMechanismRuleService extends IService<SpaceRoundMechanismRule>

{
    KnowledgeChart getRuleRoundMechanismChart();
}