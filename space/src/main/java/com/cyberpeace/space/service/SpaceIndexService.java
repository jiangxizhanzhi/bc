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
    /**
     * 获取知识点存储 库统计学
     *
     * @return 编号知识点
     * @throws
     * @see IndexKnowledge
     */
    IndexKnowledge getKnowledgeRepositoryStatistics();

    /**
     * 获取工具统计学
     *
     * @return 编号工具
     * @throws
     * @see IndexTool
     */
    IndexTool getToolStatistics();

    /**
     * 获取框架编号
     *
     * @return 编号框架
     * @throws
     * @see IndexFrame
     */
    IndexFrame getFrameIndex();

    /**
     * 获取人工智能网址
     *
     * @return 字符串
     * @throws
     * @see String
     */
    String getAiUrl();

    /**
     * 获取kchart
     *
     * @return 知识点图表
     * @throws
     * @see KnowledgeChart
     */
    KnowledgeChart getKChart();

}
