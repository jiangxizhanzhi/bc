package com.cyberpeace.evaluate.dao;

import java.util.List;

import com.cyberpeace.evaluate.dao.entity.EstimateItems;

public interface EstimateItemsDao {

	void add(EstimateItems estimateItems);

    /**
     * 根据装备评估方案编号查询评估项列表
     * @param schemeId
     * @return
     */
    List<EstimateItems> selectByScheme(String schemeId);

    void batchAdd(List<EstimateItems> estimateItems);

    void batchDelete(List<String> estimateItemIds);

    /**
     * 根据方案编号删除评估项
     * @param schemeId
     */
    void deleteByScheme(String schemeId);

    EstimateItems selectById(String itemId);
}
