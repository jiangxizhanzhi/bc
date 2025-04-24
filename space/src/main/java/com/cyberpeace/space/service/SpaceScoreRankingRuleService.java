
package com.cyberpeace.space.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.space.dao.entity.SpaceScoreRankingRule;
import com.cyberpeace.space.service.context.index.KnowledgeChart;

public interface SpaceScoreRankingRuleService extends IService<SpaceScoreRankingRule>

{
    KnowledgeChart getRuleScoreRankingChart();
}