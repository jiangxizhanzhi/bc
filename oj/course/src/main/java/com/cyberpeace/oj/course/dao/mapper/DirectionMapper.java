package com.cyberpeace.oj.course.dao.mapper;

import com.cyberpeace.oj.course.dao.entity.CourseDirection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;


@Mapper
@Repository
public interface DirectionMapper extends BaseMapper<CourseDirection> {

    CourseDirection selectByResource(@Param("direction") String direction);

}
