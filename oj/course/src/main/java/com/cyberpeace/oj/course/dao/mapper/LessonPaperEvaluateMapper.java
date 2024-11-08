package com.cyberpeace.oj.course.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.course.dao.entity.LessonPaperEvaluate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 课纸评估Mapper
 *
 * @author SN
 * @date 2024/11/08
 */
@Mapper
public interface LessonPaperEvaluateMapper extends BaseMapper<LessonPaperEvaluate> {

    /**
     * 查询由用户和课
     *
     * @param userId   用户id
     * @param lessonId 课id
     * @return {@code LessonPaperEvaluate }
     */
    LessonPaperEvaluate selectByUserAndLesson(@Param("userId") String userId,
                                              @Param("lessonId") String lessonId);

    /**
     * 查询由课和用户
     *
     * @param userIds  用户id列表
     * @param lessonId 课id
     * @return {@code List<LessonPaperEvaluate> }
     */
    List<LessonPaperEvaluate> selectByLessonAndUsers(@Param("userIds")List<String> userIds,
                                               @Param("lessonId") String lessonId);

}
