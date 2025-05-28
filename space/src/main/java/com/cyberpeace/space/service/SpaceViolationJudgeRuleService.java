
package com.cyberpeace.space.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.space.dao.entity.SpaceViolationJudgeRule;
import com.cyberpeace.space.service.context.index.KnowledgeChart;

/**
 * 空间违反法官统治Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see IService
 * @since 1.0.0
 */
public interface SpaceViolationJudgeRuleService extends IService<SpaceViolationJudgeRule>

{
    /**
     * 获取统治违反法官图表
     *
     * @return 知识点图表
     * @throws
     * @see KnowledgeChart
     */
    KnowledgeChart getRuleViolationJudgeChart();
}