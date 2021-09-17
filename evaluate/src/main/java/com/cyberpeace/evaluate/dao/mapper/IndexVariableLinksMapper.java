package com.cyberpeace.evaluate.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.cyberpeace.evaluate.dao.entity.IndexVariableLinks;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface IndexVariableLinksMapper {

	void insert(IndexVariableLinks indexVariableLinks);

	/**
	 * 更新指标项和变量项关联关系
	 * @param indexVariableLinks
	 */
	void update(IndexVariableLinks indexVariableLinks);

	/**
	 *根据方案编号和
	 * @param schemeId
	 * @param estimateIndexId
	 * @return
	 */
	List<IndexVariableLinks> selectBySchemeAndIndex(@Param("schemeId") String schemeId, @Param("estimateIndexId") String estimateIndexId);

    void batchAdd(List<IndexVariableLinks> indexVariableLinks);

	/**
	 * 批量删除
	 * @param indexVariableIds
	 */
	void batchDelete(List<String> indexVariableIds);

	/**
	 * 根据方案编号删除关联信息
	 * @param schemeId
	 */
    void deleteByScheme(String schemeId);
}
