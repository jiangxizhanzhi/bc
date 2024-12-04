package com.cyberpeace.oj.course.service;

import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.entity.LessonRecord;

import java.util.Date;
import java.util.List;

/**
 * 课记录Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
public interface LessonRecordService {

    /**
     * 查询时间范围内的学习记录
     *
     * @param userId
     * @param startTime
     * @param endTime
     * @return
     * @throws
     * @see String
     * @see Date
     * @see Date
     * @see List<LessonRecord>
     */
    List<LessonRecord> selectByTime(String userId, Date startTime, Date endTime);

    /**
     * 计算学习记录属于那些课程
     *
     * @param recordLesson
     * @param lessons
     * @return
     * @throws
     * @see List<String>
     * @see List<CourseLesson>
     * @see List<String>
     */
    List<String> recordBelongCourse(List<String> recordLesson, List<CourseLesson> lessons);

}
