package com.cyberpeace.evaluate.dao;

import java.util.List;

import com.cyberpeace.evaluate.dao.entity.EstimateIndexLinks;

public interface EstimateIndexLinksDao {

    void insert(EstimateIndexLinks estimateIndexLinks);

    /**
     * 根据装备评估方案编号和评估项查询指标项id
     * @param schemeId
     * @param estimateItemsId
     * @return
     */
    List<EstimateIndexLinks> selectBySchemeAndItem(String schemeId, String estimateItemsId);

    void batchAdd(List<EstimateIndexLinks> indexLinks);

    void batchDelete(List<String> estimateIndexIds);

    /**
     * 根据方案编号删除关联关系
     * @param schemeId
     */
    void deleteByScheme(String schemeId);
}
