
package com.cyberpeace.space.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.space.dao.entity.SpaceScoreRankingRule;
import com.cyberpeace.space.service.context.index.KnowledgeChart;

/**
 * 空间得分排名统治Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see IService
 * @since 1.0.0
 */
public interface SpaceScoreRankingRuleService extends IService<SpaceScoreRankingRule>

{
    /**
     * 获取统治得分排名图表
     *
     * @return 知识点图表
     * @throws
     * @see KnowledgeChart
     */
    KnowledgeChart getRuleScoreRankingChart();
}