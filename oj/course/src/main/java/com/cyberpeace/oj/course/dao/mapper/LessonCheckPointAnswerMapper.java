package com.cyberpeace.oj.course.dao.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.course.dao.entity.LessonCheckPointAnswer;

@Mapper
@Repository
public interface LessonCheckPointAnswerMapper extends BaseMapper<LessonCheckPointAnswer> {

	LessonCheckPointAnswer selectByLessonAndUser(@Param("lessonId") String lessonId,
			@Param("userId") String userId);
}
