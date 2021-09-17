package com.cyberpeace.evaluate.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.evaluate.dao.entity.EstimateScheme;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EstimateSchemeMapper extends BaseMapper<EstimateScheme> {

    @Override
    int insert(EstimateScheme estimateScheme);

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
     * 查询所有方案数据量
     * @return
     */
    Integer selectAllCount();

    /**
     * 更新评估方案信息
     * @param estimateScheme
     */
    void update(EstimateScheme estimateScheme);


    void delete(String schemeId);
}
