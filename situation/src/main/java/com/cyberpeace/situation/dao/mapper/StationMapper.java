package com.cyberpeace.situation.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.situation.dao.entity.Station;

@Mapper
public interface StationMapper extends BaseMapper<Station> {

	List<Station> getStations(@Param("subRangeId") String subRangeId);

}
