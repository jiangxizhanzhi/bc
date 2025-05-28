
package com.cyberpeace.space.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.space.dao.entity.SpaceRoundMechanismRule;
import com.cyberpeace.space.service.context.index.KnowledgeChart;

/**
 * 空间圆机制统治Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see IService
 * @since 1.0.0
 */
public interface SpaceRoundMechanismRuleService extends IService<SpaceRoundMechanismRule>

{
    /**
     * 获取统治圆机制图表
     *
     * @return 知识点图表
     * @throws
     * @see KnowledgeChart
     */
    KnowledgeChart getRuleRoundMechanismChart();
}