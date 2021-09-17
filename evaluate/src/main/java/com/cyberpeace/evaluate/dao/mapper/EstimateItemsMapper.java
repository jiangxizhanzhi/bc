package com.cyberpeace.evaluate.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cyberpeace.evaluate.dao.entity.EstimateItems;

@Mapper
public interface EstimateItemsMapper {

    void insert(EstimateItems estimateItems);

    /**
     * 根据装备评估方案编号查询评估项列表
     * @param schemeId
     * @return
     */
    List<EstimateItems> selectByScheme(String schemeId);

    void batchAdd(List<EstimateItems> estimateItems);

    void batchDelete(List<String> estimateItemIds);

    /**
     * 根据方案编号删除评估项信息
     * @param schemeId
     */
    void deleteByScheme(String schemeId);

    EstimateItems selectById(@Param("itemId") String itemId);
}
