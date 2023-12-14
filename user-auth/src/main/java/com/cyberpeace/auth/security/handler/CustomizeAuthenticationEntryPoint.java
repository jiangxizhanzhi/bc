package com.cyberpeace.auth.security.handler;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.cyberpeace.auth.security.handler.bo.AuthFailureRsp;
import com.cyberpeace.auth.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 匿名用户访问无权限资源时的异常
 */
@Component
@Slf4j
public class CustomizeAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
		CommonUtil.responseJson(httpServletResponse, new AuthFailureRsp("未登录"), SC_UNAUTHORIZED);
    }
}
