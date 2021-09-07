package com.cyberpeace.oj.course.service;

import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.oj.course.dao.context.AuthContext;

public interface CourseAuthConstructService {

    /**
     * 前台课程访问权限
     * @param userInfo
     * @return
     */
    AuthContext webCourseConstruct(UserInfo userInfo);

    /**
     * 构造课程后台访问权限
     * @param userInfo
     * @return
     */
    AuthContext adminCourseConstruct(UserInfo userInfo);

}
