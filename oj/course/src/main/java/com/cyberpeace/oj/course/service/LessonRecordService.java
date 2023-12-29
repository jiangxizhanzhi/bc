package com.cyberpeace.oj.course.service;

import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.entity.LessonRecord;

import java.util.Date;
import java.util.List;

public interface LessonRecordService {

    /**
     * 查询时间范围内的学习记录
     * @param userId
     * @param startTime
     * @param endTime
     * @return
     */
    List<LessonRecord> selectByTime(String userId, Date startTime, Date endTime);

    /**
     * 计算学习记录属于那些课程
     * @param recordLesson
     * @param lessons
     * @return
     */
    List<String> recordBelongCourse(List<String> recordLesson, List<CourseLesson> lessons);

    /**
     * 获取用户学习记录
     * @param userId
     * @return
     */
    List<LessonRecord>  selectByUser(String userId);

    /**
     * 查询用户课程学习记录
     * @param courseId
     * @param userId
     * @return
     */
    List<LessonRecord> selectByCourseAndUser(String courseId, String userId);
}
