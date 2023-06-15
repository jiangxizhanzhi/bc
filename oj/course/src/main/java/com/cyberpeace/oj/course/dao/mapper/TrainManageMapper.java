package com.cyberpeace.oj.course.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.course.dao.entity.TrainManage;
import com.cyberpeace.oj.course.service.bo.TrainCourseBo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TrainManageMapper extends BaseMapper<TrainManage> {

    List<TrainCourseBo> selectCourseInfo(@Param("courseIds") List<String> courseIds);

    List<TrainCourseBo> selectCourseIntroduce(@Param("courseIds") List<String> courseIds);

    List<String> selectCourseLessonBycourseId(@Param("courseId") String courseId);
}
