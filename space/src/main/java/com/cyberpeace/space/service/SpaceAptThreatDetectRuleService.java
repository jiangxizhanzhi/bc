
package com.cyberpeace.space.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.space.dao.entity.SpaceAptThreatDetectRule;
import com.cyberpeace.space.service.context.index.KnowledgeChart;

/**
 * 空间容易威胁检测统治Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see IService
 * @since 1.0.0
 */
public interface SpaceAptThreatDetectRuleService extends IService<SpaceAptThreatDetectRule>

{
    /**
     * 获取统治容易威胁图表
     *
     * @return 知识点图表
     * @throws
     * @see KnowledgeChart
     */
    KnowledgeChart getRuleAptThreatChart();
}