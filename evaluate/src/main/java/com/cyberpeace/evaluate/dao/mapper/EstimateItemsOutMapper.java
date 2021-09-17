package com.cyberpeace.evaluate.dao.mapper;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.evaluate.dao.entity.EstimateItemsOut;

@Mapper
public interface EstimateItemsOutMapper extends BaseMapper<EstimateItemsOut> {

    @Override
    int insert(EstimateItemsOut estimateItemsOut);
}
