package com.cyberpeace.oj.course.dao.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.course.dao.entity.LessonCheckPointAnswer;

@Mapper
@Repository
public interface LessonCheckPointAnswerMapper extends BaseMapper<LessonCheckPointAnswer> {

	List<LessonCheckPointAnswer> selectByLessonAndUser(@Param("lessonId") String lessonId,
			@Param("userId") String userId);

	LessonCheckPointAnswer selectByLessonAndUserAndTime(@Param("lessonId") String lessonId,
			@Param("userId") String userId, @Param("time") String time);
}
