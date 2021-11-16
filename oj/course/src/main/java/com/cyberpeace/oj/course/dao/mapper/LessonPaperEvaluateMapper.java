package com.cyberpeace.oj.course.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.course.dao.entity.LessonPaperEvaluate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LessonPaperEvaluateMapper extends BaseMapper<LessonPaperEvaluate> {

    LessonPaperEvaluate selectByUserAndLesson(@Param("userId") String userId,
                                              @Param("lessonId") String lessonId);

}
