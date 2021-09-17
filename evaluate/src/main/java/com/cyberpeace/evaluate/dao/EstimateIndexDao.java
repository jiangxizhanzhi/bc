package com.cyberpeace.evaluate.dao;

import java.util.List;

import com.cyberpeace.evaluate.dao.entity.EstimateIndex;

public interface EstimateIndexDao {

    /**
     * 新增评估指标项
     * @param estimateIndex
     */
    void insert(EstimateIndex estimateIndex);

    /**
     * 根据装备评估方案查询指标项列表
     * @param schemeId
     * @return
     */
    List<EstimateIndex> selectByScheme(String schemeId);

    void batchDelete(List<String> indexIds);

    /**
     * 根据方案编号删除指标项
     * @param schemeId
     */
    void deleteByScheme(String schemeId);

    EstimateIndex selectById(String indexId);
}
