package com.cyberpeace.oj.course.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.course.dao.entity.CourseStudyHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface CourseStudyHistoryMapper extends BaseMapper<CourseStudyHistory> {

	List<CourseStudyHistory> selectByUserId(@Param("userId") String userId);

	CourseStudyHistory selectByCourseIdAndUserId(@Param("courseId") String courseId,@Param("userId") String userId);

}
