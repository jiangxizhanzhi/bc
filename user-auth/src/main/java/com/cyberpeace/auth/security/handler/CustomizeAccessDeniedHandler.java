package com.cyberpeace.auth.security.handler;

import static javax.servlet.http.HttpServletResponse.SC_FORBIDDEN;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import com.cyberpeace.auth.security.handler.bo.AuthFailureRsp;
import com.cyberpeace.auth.util.CommonUtil;

/**
 *
 * 权限拒绝处理逻辑
 */
@Component
public class CustomizeAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AccessDeniedException e) throws IOException, ServletException {
		CommonUtil.responseJson(httpServletResponse, new AuthFailureRsp("未授权"), SC_FORBIDDEN);
    }
}
