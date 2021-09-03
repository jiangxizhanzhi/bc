package com.cyberpeace.oj.course.service;

import com.cyberpeace.oj.course.service.bo.LessonConfigBo;

public interface LessonConfigService {

    /**
     * 根据课程编号用户编号查询课程目录
     * @param courseId
     * @param userId
     * @return
     */
    LessonConfigBo getLessonConfig(String courseId, String userId);
}
