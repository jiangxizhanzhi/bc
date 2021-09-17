package com.cyberpeace.evaluate.dao;

import java.util.List;

import com.cyberpeace.evaluate.dao.entity.IndexVariableLinks;


public interface IndexVariableLinksDao {

    void insert(IndexVariableLinks indexVariableLinks);

    /**
     * 查询对应指标项的变量项
     * @param schemeId
     * @param estimateIndexId
     * @return
     */
    List<IndexVariableLinks> selectBySchemeAndIndex(String schemeId, String estimateIndexId);

    void batchAdd(List<IndexVariableLinks> indexVariableLinks);

    /**
     * 批量删除
     * @param indexVariableIds
     */
    void batchDelete(List<String> indexVariableIds);

    /**
     * 根据任务编号删除关联信息
     * @param schemeId
     */
    void deleteByScheme(String schemeId);
}
