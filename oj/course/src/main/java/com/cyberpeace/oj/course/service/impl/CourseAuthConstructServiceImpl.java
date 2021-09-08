package com.cyberpeace.oj.course.service.impl;

import org.springframework.stereotype.Service;

import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.oj.course.dao.context.AuthContext;
import com.cyberpeace.oj.course.service.CourseAuthConstructService;

@Service
public class CourseAuthConstructServiceImpl implements CourseAuthConstructService {


    @Override
    public AuthContext webCourseConstruct(UserInfo userInfo) {
        if (userInfo.isSuperuser()) {
            return null;
        }

        AuthContext authContext = new AuthContext();
        authContext.setOrganization(userInfo.getOrganizationId());
        authContext.setUserId(userInfo.getUserId());
        authContext.setIsWeb(true);
        return authContext;
    }

    @Override
    public AuthContext adminCourseConstruct(UserInfo userInfo) {
        if (userInfo.isSuperuser()) {
            return null;
        }
        AuthContext authContext = new AuthContext();
        authContext.setOrganization(userInfo.getOrganizationId());
        authContext.setUserId(userInfo.getUserId());
        authContext.setIsWeb(false);
        return authContext;
    }
}
