package com.cyberpeace.auth.security.handler;

import static javax.servlet.http.HttpServletResponse.SC_OK;

import java.io.IOException;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cyberpeace.auth.constant.*;
import com.cyberpeace.auth.security.TbUser;
import com.cyberpeace.auth.service.OpLogService;
import com.cyberpeace.auth.service.SysUserService;
import com.cyberpeace.auth.service.bo.UserInfoContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import com.cyberpeace.auth.util.CommonUtil;
import com.cyberpeace.common.model.BaseRsp;

/**
 * 登出成功处理逻辑
 */
@Component
@Slf4j
public class CustomizeLogoutSuccessHandler implements LogoutSuccessHandler {
    @Autowired
    private OpLogService opLogService;
    @Autowired
    SysUserService sysUserService;
    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        // 审计日志
        if (authentication!=null){
            TbUser tbUser = (TbUser) authentication.getPrincipal();
            String ip = CommonUtil.getIpAddr(httpServletRequest);
            opLogService.save(OpLogModuleEnum.USER.getType(),
                    OpLogMethodEnum.LOGOUT.getType(), OpLogTarget.USER,
                    String.format(OpLogDetail.USER_LOGOUT, ip),tbUser);
            log.info("==============登出======================================");
            Date loginTime = OnlineTimeCache.remove(tbUser.getUserId());
            sysUserService.updateOnlineTime(loginTime,tbUser);
        }
        CommonUtil.responseJson(httpServletResponse, new BaseRsp("退出成功"), SC_OK);
    }
}
