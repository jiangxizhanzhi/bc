
package com.cyberpeace.space.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.space.dao.entity.SpaceAptFeature;
import com.cyberpeace.space.service.context.index.KnowledgeChart;

public interface SpaceAptFeatureService extends IService<SpaceAptFeature>

{
    KnowledgeChart getRuleAptOrgChart();
}