package com.cyberpeace.space.service;

import com.cyberpeace.space.service.context.index.IndexFrame;
import com.cyberpeace.space.service.context.index.IndexKnowledge;
import com.cyberpeace.space.service.context.index.IndexTool;
import com.cyberpeace.space.service.context.index.KnowledgeChart;

public interface SpaceIndexService {
    IndexKnowledge getKnowledgeRepositoryStatistics();

    IndexTool getToolStatistics();

    IndexFrame getFrameIndex();

    String getAiUrl();

    KnowledgeChart getKChart();

}
