package com.cyberpeace.situation.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.dao.entity.Center;

@Mapper
public interface CenterMapper extends BaseMapper<Center> {

	List<Center> getCenters();

	Center getById(@Param("centerId") String centerId);
}
