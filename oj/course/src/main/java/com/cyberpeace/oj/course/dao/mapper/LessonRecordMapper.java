package com.cyberpeace.oj.course.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.course.dao.entity.LessonRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

@Mapper
public interface LessonRecordMapper extends BaseMapper<LessonRecord> {

	List<LessonRecord> selectByUser(@Param("userId") String userId);

	List<LessonRecord> listByLessonAndUser(@Param("userId") String userId,
			@Param("lessonIds") List<String> lessonIds);

	List<LessonRecord> selectByTime(@Param("userId") String userId, @Param("start") Date startTime,
			@Param("end") Date endTime);
}
