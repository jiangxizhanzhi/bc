package com.cyberpeace.oj.ojbase.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.ojbase.dao.entity.Indicator;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface IndicatorMapper extends BaseMapper<Indicator> {
}
