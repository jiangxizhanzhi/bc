
package com.cyberpeace.space.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.space.dao.entity.SpaceTrafficDetectRule;
import com.cyberpeace.space.service.context.index.KnowledgeChart;

public interface SpaceTrafficDetectRuleService extends IService<SpaceTrafficDetectRule>

{
    KnowledgeChart getRuleNetworkChart();

}