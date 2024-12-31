package com.cyberpeace.space.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.space.dao.entity.SpaceAptReport;

@Mapper
@Repository
public interface SpaceAptReportMapper extends BaseMapper<SpaceAptReport> {}
