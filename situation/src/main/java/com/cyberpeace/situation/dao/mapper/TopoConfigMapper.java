package com.cyberpeace.situation.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.dao.entity.TopoConfig;

@Mapper
public interface TopoConfigMapper extends BaseMapper<TopoConfig> {

	TopoConfig selectByEnvId(@Param("envId") String envId);

}
