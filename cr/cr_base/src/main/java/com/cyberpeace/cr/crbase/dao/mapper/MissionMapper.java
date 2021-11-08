package com.cyberpeace.cr.crbase.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.cr.crbase.dao.entity.Mission;

@Mapper
@Repository
public interface MissionMapper extends BaseMapper<Mission> {}
