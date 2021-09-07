package com.cyberpeace.oj.course.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.course.dao.entity.CourseCollection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface CourseCollectionMapper extends BaseMapper<CourseCollection> {

    CourseCollection selectByUserAndCourse(@Param("userId") String userId,
                                           @Param("course") String course);

    void update(@Param("collection") CourseCollection collection);

}
