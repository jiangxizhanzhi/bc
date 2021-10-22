package com.cyberpeace.oj.course.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.oj.course.controller.res.AdminLessonDetailRes;
import com.cyberpeace.oj.course.dao.entity.CourseLesson;
import com.cyberpeace.oj.course.dao.entity.LessonScene;
import com.cyberpeace.oj.course.service.bo.AdminLessonBo;
import com.cyberpeace.oj.course.service.bo.LessonConfigBo;
import com.cyberpeace.oj.course.service.bo.LessonInfoBo;

public interface CourseLessonService extends IService<CourseLesson> {

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
     * 收藏课时
     * @param userId
     * @param lessonId
     * @param isCollection
     * @return
     */
    String collectionLesson(String userId, String lessonId, Boolean isCollection);

    /**
     * 查询后台课时详情数据
     * @param courseId
     * @param lessonId
     * @return
     */
    AdminLessonBo selectByCourseAndLesson(String courseId, String lessonId);
}
