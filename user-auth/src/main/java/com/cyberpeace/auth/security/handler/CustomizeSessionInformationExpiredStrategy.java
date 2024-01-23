package com.cyberpeace.auth.security.handler;

import static javax.servlet.http.HttpServletResponse.SC_UNAUTHORIZED;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.session.SessionInformationExpiredEvent;
import org.springframework.security.web.session.SessionInformationExpiredStrategy;
import org.springframework.stereotype.Component;

import com.cyberpeace.auth.constant.OnlineTimeCache;
import com.cyberpeace.auth.security.TbUser;
import com.cyberpeace.auth.security.handler.bo.AuthFailureRsp;
import com.cyberpeace.auth.service.SysUserService;
import com.cyberpeace.auth.util.CommonUtil;

import lombok.extern.slf4j.Slf4j;


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
        TbUser tbUser;
        if(event.getSessionInformation().getPrincipal() instanceof String){
			tbUser = new TbUser(sysUserService.getOrExceptionByUsername(
					(String) event.getSessionInformation().getPrincipal()));
		} else {
			tbUser = (TbUser) event.getSessionInformation().getPrincipal();
		}
        Date loginTime = OnlineTimeCache.remove(tbUser.getUserId());
        sysUserService.updateOnlineTime(loginTime,tbUser.getUserId());
        HttpServletResponse httpServletResponse = event.getResponse();
		CommonUtil.responseJson(httpServletResponse, new AuthFailureRsp("账号下线"), SC_UNAUTHORIZED);

    }
}
