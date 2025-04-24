
package com.cyberpeace.space.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.space.dao.entity.SpaceViolationJudgeRule;
import com.cyberpeace.space.service.context.index.KnowledgeChart;

public interface SpaceViolationJudgeRuleService extends IService<SpaceViolationJudgeRule>

{
    KnowledgeChart getRuleViolationJudgeChart();
}