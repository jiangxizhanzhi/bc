package com.cyberpeace.oj.course.dao.mapper;

import com.cyberpeace.oj.course.dao.entity.CourseDirection;
import com.cyberpeace.oj.course.dao.oj_entity.Direction;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


@Mapper
@Repository
public interface DirectionMapper extends BaseMapper<CourseDirection> {

    CourseDirection selectByResource(@Param("direction") String direction);

    List<Direction> selectChirdlentByCode(@Param("directionId") String directionId);
}
