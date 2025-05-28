package com.cyberpeace.space.service;

import com.cyberpeace.space.service.context.index.IndexFrame;
import com.cyberpeace.space.service.context.index.IndexKnowledge;
import com.cyberpeace.space.service.context.index.IndexTool;
import com.cyberpeace.space.service.context.index.KnowledgeChart;

/**
 * 空间编号Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
public interface SpaceIndexService {
    IndexKnowledge getKnowledgeRepositoryStatistics();

    IndexTool getToolStatistics();

    IndexFrame getFrameIndex();

    String getAiUrl();

    KnowledgeChart getKChart();

}
