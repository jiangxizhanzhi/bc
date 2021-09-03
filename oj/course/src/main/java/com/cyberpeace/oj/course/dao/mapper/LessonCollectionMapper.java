package com.cyberpeace.oj.course.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.course.dao.entity.LessonCollection;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface LessonCollectionMapper extends BaseMapper<LessonCollection> {

    LessonCollection selectByUserAndLesson(@Param("userId") String userId,
                                           @Param("lessonId") String lessonId);

    void update(@Param("lessonCollection") LessonCollection collection);
}
