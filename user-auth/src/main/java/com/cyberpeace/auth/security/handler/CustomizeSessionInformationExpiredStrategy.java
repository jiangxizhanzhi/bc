package com.cyberpeace.auth.security.handler;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import com.cyberpeace.auth.constant.OnlineTimeCache;
import com.cyberpeace.auth.domain.SysUser;
import com.cyberpeace.auth.security.TbUser;
import com.cyberpeace.auth.service.SysUserService;
import com.cyberpeace.auth.service.bo.UserInfoContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@Slf4j
public class CustomizeSessionInformationExpiredStrategy implements SessionInformationExpiredStrategy {
    @Autowired
    SysUserService sysUserService;
    @Override
    public void onExpiredSessionDetected(SessionInformationExpiredEvent event) throws IOException, ServletException {
        log.info("==============会话信息过期======================================");
        TbUser tbUser = (TbUser) event.getSessionInformation().getPrincipal();
        Date loginTime = OnlineTimeCache.remove(tbUser.getUserId());
        sysUserService.updateOnlineTime(loginTime,tbUser.getUserId());
        HttpServletResponse httpServletResponse = event.getResponse();
		CommonUtil.responseJson(httpServletResponse, new AuthFailureRsp("账号下线"), SC_UNAUTHORIZED);

    }
}
