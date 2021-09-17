package com.cyberpeace.evaluate.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cyberpeace.evaluate.dao.entity.EstimateVariable;

@Mapper
public interface EstimateVariableMapper {

	void insert(EstimateVariable estimateVariable);

	/**
	 * 更新评估变量项详细信息
	 * @param estimateVariable
	 */
	void update(EstimateVariable estimateVariable);

	EstimateVariable getById(@Param("variableId") String variableId);

	/**
	 * 根据装备评估方案查询评估变量列表
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
