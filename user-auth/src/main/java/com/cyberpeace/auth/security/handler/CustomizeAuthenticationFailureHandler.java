package com.cyberpeace.auth.security.handler;

import com.cyberpeace.auth.security.handler.bo.AuthFailureRsp;
import com.cyberpeace.auth.util.CommonUtil;
import com.cyberpeace.common.model.BaseRsp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.*;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static javax.servlet.http.HttpServletResponse.SC_INTERNAL_SERVER_ERROR;

/**
 * 登录失败处理逻辑
 */
@Component
@Slf4j
public class CustomizeAuthenticationFailureHandler implements AuthenticationFailureHandler {


    @Override
    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {

        if (e instanceof AccountExpiredException) {
            //账号过期
            log.error("login error account expired exception" + e.getMessage());
            CommonUtil.responseJson(response, new AuthFailureRsp("账号过期"), SC_INTERNAL_SERVER_ERROR);

        } else if (e instanceof BadCredentialsException) {
            //密码错误
            log.error("login error password exception" + e.getMessage());
            CommonUtil.responseJson(response, new AuthFailureRsp("密码错误"), SC_INTERNAL_SERVER_ERROR);

        } else if (e instanceof CredentialsExpiredException) {
            //密码过期
            log.error("login error password  expired" + e.getMessage());
            CommonUtil.responseJson(response, new AuthFailureRsp("用户密码过期"), SC_INTERNAL_SERVER_ERROR);

        } else if (e instanceof DisabledException) {
            //账号不可用
            log.error("login error account disable " + e.getMessage());
            CommonUtil.responseJson(response, new AuthFailureRsp("账号不可用"), SC_INTERNAL_SERVER_ERROR);

        } else if (e instanceof LockedException) {
            //账号锁定
            log.error("login error account locked" + e.getMessage());
            CommonUtil.responseJson(response, new AuthFailureRsp("账号锁定"), SC_INTERNAL_SERVER_ERROR);

        } else if (e instanceof UsernameNotFoundException) {
            //用户不存在
            log.error("login error account not exist" + e.getMessage());
            CommonUtil.responseJson(response, new AuthFailureRsp(e.getMessage()), SC_INTERNAL_SERVER_ERROR);

        } else if (e instanceof SessionAuthenticationException) {
            log.error("login error account login device limit reached " + e.getMessage());
            CommonUtil.responseJson(response, new AuthFailureRsp("账号登录设备数已达到最大数"), SC_INTERNAL_SERVER_ERROR);

        } else {
            log.error("e", e);
            //其他错误
            CommonUtil.responseJson(response, new AuthFailureRsp("登录失败"), SC_INTERNAL_SERVER_ERROR);
        }
    }
}
