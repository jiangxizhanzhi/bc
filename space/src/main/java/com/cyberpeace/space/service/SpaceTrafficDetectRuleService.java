
package com.cyberpeace.space.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.space.dao.entity.SpaceTrafficDetectRule;
import com.cyberpeace.space.service.context.index.KnowledgeChart;

/**
 * 空间流量检测统治Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see IService
 * @since 1.0.0
 */
public interface SpaceTrafficDetectRuleService extends IService<SpaceTrafficDetectRule>

{
    KnowledgeChart getRuleNetworkChart();

}