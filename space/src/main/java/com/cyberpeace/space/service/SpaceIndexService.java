package com.cyberpeace.space.service;

import com.cyberpeace.space.service.context.index.IndexFrame;
import com.cyberpeace.space.service.context.index.IndexKnowledge;
import com.cyberpeace.space.service.context.index.IndexTool;

public interface SpaceIndexService {
    IndexKnowledge getKnowledgeRepositoryStatistics();

    IndexTool getToolStatistics();

    IndexFrame getFrameIndex();
}
