package com.cyberpeace.oj.course.service;

import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.entity.LessonRecord;
import com.cyberpeace.oj.course.dao.entity.LessonScene;
import com.cyberpeace.oj.course.service.bo.LessonConfigBo;
import com.cyberpeace.oj.course.service.bo.LessonInfoBo;

import java.util.Date;
import java.util.List;

public interface CourseLessonService {

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
     * 根据课程编号和课时编号查询课时基本信息
     * @param courseId
     * @param lessonId
     * @return
     */
    LessonInfoBo getLessonDetail(String userId, String courseId, String lessonId);

    /**
     * 根据课时编号查询课时启动的场景
     * @param user
     * @param lessonId
     * @return
     */
    LessonScene getLessonScene(UserInfo user, String lessonId);

    /**
     * 根据课程编号用户编号查询课程目录
     * @param courseId
     * @param userId
     * @return
     */
    LessonConfigBo getLessonConfig(String courseId, String userId);

    /**
     * 收藏课时
     * @param userId
     * @param lessonId
     * @param isCollection
     * @return
     */
    String collectionLesson(String userId, String lessonId, Boolean isCollection);
}
