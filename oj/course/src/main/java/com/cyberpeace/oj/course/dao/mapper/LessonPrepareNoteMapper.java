package com.cyberpeace.oj.course.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.course.dao.entity.LessonPrepareNote;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface LessonPrepareNoteMapper extends BaseMapper<LessonPrepareNote> {
    String selectNoteByLessonAndUser(@Param("lessonId") String lessonId, @Param("userId") String userId);

    LessonPrepareNote getByLessonAndUser(@Param("lessonId") String lessonId, @Param("userId") String userId);

    void updateByLessonAndUser(@Param("lessonId") String lessonId, @Param("userId") String userId, @Param("note") String note);
}
