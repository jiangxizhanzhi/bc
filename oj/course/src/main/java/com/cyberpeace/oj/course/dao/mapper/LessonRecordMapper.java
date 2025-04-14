package com.cyberpeace.oj.course.dao.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cyberpeace.oj.course.dao.entity.LessonRecord;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

/**
 * 课时记录Mapper
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @see BaseMapper
 * @since 1.0.0
 */
@Mapper
public interface LessonRecordMapper extends BaseMapper<LessonRecord> {

    /**
     * 查询由用户
     *
     * @param userId 用户id
     * @return {@code List<LessonRecord> }
     * @throws
     * @see String
     * @see List<LessonRecord>
     */
    List<LessonRecord> selectByUser(@Param("userId") String userId);

    /**
     * 列表由课时和用户
     *
     * @param userId    用户id
     * @param lessonIds 课时id列表
     * @return {@code List<LessonRecord> }
     * @throws
     * @see String
     * @see List<String>
     * @see List<LessonRecord>
     */
    List<LessonRecord> listByLessonAndUser(@Param("userId") String userId,
                                           @Param("lessonIds") List<String> lessonIds);

    /**
     * 查询由时间
     *
     * @param userId    用户id
     * @param startTime 启动时间
     * @param endTime   结束时间
     * @return {@code List<LessonRecord> }
     * @throws
     * @see String
     * @see Date
     * @see Date
     * @see List<LessonRecord>
     */
    List<LessonRecord> selectByTime(@Param("userId") String userId, @Param("start") Date startTime,
                                    @Param("end") Date endTime);

    /**
     * 查询由课时和用户
     *
     * @param lessonId 课时id
     * @param userId   用户id
     * @return {@code LessonRecord }
     * @throws
     * @see String
     * @see String
     * @see LessonRecord
     */
    LessonRecord selectByLessonAndUser(@Param("lessonId") String lessonId,
                                       @Param("userId") String userId);

    /**
     * 更新学习时间
     *
     * @param lessonId    课时id
     * @param courseId    课程id
     * @param userId      用户id
     * @param learnedType 学习类型
     * @param roomId      房间id
     * @return int
     * @throws
     * @see String
     * @see String
     * @see String
     * @see Integer
     * @see String
     * @see int
     */
    int updateLearnedTime(@Param("lessonId") String lessonId, @Param("courseId") String courseId, @Param("userId") String userId,
                          @Param("learnedType") Integer learnedType, @Param("roomId") String roomId);

    Integer getTrainingDurationByCourseId(@Param("courseId") String courseId, @Param("userId") String userId);

    Integer recordTime(@Param("lessonId") String lessonId, @Param("courseId") String courseId,
                       @Param("learnedTime") String learnedTime, @Param("learnedDuration") Integer learnedDuration,
                       @Param("userId") String userId);

    LessonRecord getRecord(@Param("lessonId") String lessonId, @Param("courseId") String courseId, @Param("userId") String userId);
}
