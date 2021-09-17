package com.cyberpeace.evaluate.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.evaluate.dao.entity.EstimateScheme;
import com.cyberpeace.evaluate.service.context.EstimateSchemeContext;

public interface EstimateSchemeService {

    /**
     * 分页查询装备评估方案列表
     * @param pageBase
     * @return
     */
    ListRsp<EstimateScheme> selectList(Page pageBase);

    /**
     * 根据方案编号查询方案
     * @param schemeId
     * @return
     */
    EstimateSchemeContext selectById(String schemeId);

    /**
     * 新增装备评估方案
     * @param estimateSchemeContext
     */
    String add(EstimateSchemeContext estimateSchemeContext);

    void deleteById(String schemeId);

}
