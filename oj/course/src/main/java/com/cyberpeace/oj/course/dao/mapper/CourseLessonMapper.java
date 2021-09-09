package com.cyberpeace.oj.course.dao.mapper;

import java.util.List;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.oj.course.dao.context.LessonContext;
import com.cyberpeace.oj.course.dao.oj_entity.Lesson;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;

// 旧表对应mapper
@Mapper
@Repository
public interface CourseLessonMapper extends BaseMapper<CourseLesson> {

    List<CourseLesson> listByCourses(@Param("courseIds") List<String> courseIds);

}
