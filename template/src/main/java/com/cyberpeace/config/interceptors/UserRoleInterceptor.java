package com.cyberpeace.config.interceptors;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authc.AuthenticationException;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import com.cyberpeace.auth.service.bo.UserInfoContext;
import com.cyberpeace.common.annoations.AdminRequired;
import com.cyberpeace.common.model.user.UserInfo;

import lombok.extern.slf4j.Slf4j;

/**
 * 校验管理员权限
 * @author SN
 * @date 2023/12/14
 */
@Slf4j
@Component
public class UserRoleInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                             Object handler) {
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }
        Method method = ((HandlerMethod) handler).getMethod();
        AdminRequired adminRequired = method.getAnnotation(AdminRequired.class);
        if (null == adminRequired) {
            return true;
        }
        UserInfo userInfo = UserInfoContext.getUserContext();
        if (!userInfo.isAdmin()) {
			throw new AuthenticationException();
        }
        return true;
    }
}
