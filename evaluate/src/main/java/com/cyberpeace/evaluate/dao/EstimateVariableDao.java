package com.cyberpeace.evaluate.dao;

import java.util.List;

import com.cyberpeace.evaluate.dao.entity.EstimateVariable;

public interface EstimateVariableDao {

    void insert(EstimateVariable estimateVariable);

    EstimateVariable getById(String variableId);

    /**
     * 根据装备评估方案查询评估变量项列表
     * @param schemeId
     * @return
     */
    List<EstimateVariable> selectBySchemeId(String schemeId);

    void batchDelete(List<String> variableIds);

    /**
     * 根据方案编号删除变量项
     * @param schemeId
     */
    void deleteByScheme(String schemeId);
}
