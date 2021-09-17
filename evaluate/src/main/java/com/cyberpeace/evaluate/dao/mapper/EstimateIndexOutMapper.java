package com.cyberpeace.evaluate.dao.mapper;

import com.cyberpeace.evaluate.dao.entity.EstimateIndexOut;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface EstimateIndexOutMapper {

    void insert(EstimateIndexOut estimateIndexOut);

    EstimateIndexOut select(EstimateIndexOut estimateIndexOut);
}
