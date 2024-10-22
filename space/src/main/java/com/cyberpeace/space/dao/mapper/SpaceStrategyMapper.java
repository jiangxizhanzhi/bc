package com.cyberpeace.space.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.space.dao.entity.SpaceStrategy;
import com.cyberpeace.space.service.context.strategy.SpaceStrategySimple;

@Mapper
@Repository
public interface SpaceStrategyMapper extends BaseMapper<SpaceStrategy> {

	List<SpaceStrategySimple> selectIdAndNameByFrame(@Param("frameId") String frameId,
			@Param("name") String name);
}
