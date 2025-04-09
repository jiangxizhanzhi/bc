package com.cyberpeace.oj.ojbase.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.ojbase.dao.entity.IndicatorMetrics;

@Mapper
@Repository
public interface IndicatorMetricsMapper extends BaseMapper<IndicatorMetrics> {

}
