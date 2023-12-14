package com.cyberpeace.auth.security.handler;

import static javax.servlet.http.HttpServletResponse.SC_OK;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.cyberpeace.auth.util.CommonUtil;
import com.cyberpeace.common.model.BaseRsp;

/**
 * 登出成功处理逻辑
 */
@Component
public class CustomizeLogoutSuccessHandler implements LogoutSuccessHandler {

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        CommonUtil.responseJson(httpServletResponse, new BaseRsp("退出成功"), SC_OK);
    }
}
