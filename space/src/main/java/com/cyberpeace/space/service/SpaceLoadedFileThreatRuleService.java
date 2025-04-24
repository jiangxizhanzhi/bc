
package com.cyberpeace.space.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.space.dao.entity.SpaceLoadedFileThreatRule;
import com.cyberpeace.space.service.context.index.KnowledgeChart;

public interface SpaceLoadedFileThreatRuleService extends IService<SpaceLoadedFileThreatRule> {

    KnowledgeChart geRuleVirusChart();
}