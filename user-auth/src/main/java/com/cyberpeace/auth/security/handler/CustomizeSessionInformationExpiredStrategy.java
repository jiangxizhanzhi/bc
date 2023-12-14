package com.cyberpeace.auth.security.handler;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import com.cyberpeace.auth.security.handler.bo.AuthFailureRsp;
import com.cyberpeace.auth.util.CommonUtil;


/**
 *
 * 自定义会话信息过期策略
 */
@Component
public class CustomizeSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = event.getResponse();
		CommonUtil.responseJson(httpServletResponse, new AuthFailureRsp("账号下线"), SC_UNAUTHORIZED);

    }
}
