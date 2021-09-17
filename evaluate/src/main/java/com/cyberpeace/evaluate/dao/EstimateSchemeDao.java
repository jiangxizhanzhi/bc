package com.cyberpeace.evaluate.dao;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.evaluate.dao.entity.EstimateScheme;

public interface EstimateSchemeDao {

	/**
	 * 分页查询装备评估方案列表
	 * @param pageBase
	 * @return
	 */
	IPage<EstimateScheme> selectList(Page pageBase);

	/**
	 * 根据方案编号查询方案
	 * @param schemeId
	 * @return
	 */
	EstimateScheme selectById(String schemeId);

	/**
	 * 新增评估方案
	 * @param estimateScheme
	 */
	void insert(EstimateScheme estimateScheme);

    void delete(String schemeId);
}
