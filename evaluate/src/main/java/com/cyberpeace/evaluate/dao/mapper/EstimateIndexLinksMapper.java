package com.cyberpeace.evaluate.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cyberpeace.evaluate.dao.entity.EstimateIndexLinks;

@Mapper
public interface EstimateIndexLinksMapper {

	void insert(EstimateIndexLinks estimateIndexLinks);

	/**
	 * 更新评估项指标项关联表
	 * @param estimateIndexLinks
	 */
	void update(EstimateIndexLinks estimateIndexLinks);

	/**
	 * 根据方案编号和评估项编号查询链接关系列表
	 * @param schemeId
	 * @param estimateItemsId
	 * @return
	 */
	List<EstimateIndexLinks> selectBySchemeAndItem(@Param("schemeId") String schemeId,
			@Param("estimateItemsId") String estimateItemsId);

    void batchAdd(List<EstimateIndexLinks> indexLinks);

	void batchDelete(List<String> estimateIndexIds);

    void deleteByScheme(String schemeId);
}
