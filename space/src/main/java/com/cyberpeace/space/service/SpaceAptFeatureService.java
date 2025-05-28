
package com.cyberpeace.space.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.space.dao.entity.SpaceAptFeature;
import com.cyberpeace.space.service.context.index.KnowledgeChart;

/**
 * 空间容易特征Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see IService
 * @since 1.0.0
 */
public interface SpaceAptFeatureService extends IService<SpaceAptFeature>

{
    KnowledgeChart getRuleAptOrgChart();
}