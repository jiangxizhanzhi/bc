package com.cyberpeace.evaluate.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cyberpeace.evaluate.dao.entity.EstimateIndex;

@Mapper
public interface EstimateIndexMapper {

    void insert(EstimateIndex estimateIndex);

    /**
     * 更新指标项信息
     * @param estimateIndex
     */
    void update(EstimateIndex estimateIndex);

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

    EstimateIndex selectById(@Param("indexId") String indexId);
}
