package com.cyberpeace.oj.course.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.course.dao.entity.Direction;


@Mapper
@Repository
public interface DirectionMapper extends BaseMapper<Direction> {

    Direction selectByResource(@Param("direction") String direction);

}
