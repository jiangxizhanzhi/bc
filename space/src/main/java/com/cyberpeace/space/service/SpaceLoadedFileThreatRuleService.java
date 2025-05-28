
package com.cyberpeace.space.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.space.dao.entity.SpaceLoadedFileThreatRule;
import com.cyberpeace.space.service.context.index.KnowledgeChart;

/**
 * 空间加载文件威胁统治Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see IService
 * @since 1.0.0
 */
public interface SpaceLoadedFileThreatRuleService extends IService<SpaceLoadedFileThreatRule> {

    KnowledgeChart geRuleVirusChart();
}