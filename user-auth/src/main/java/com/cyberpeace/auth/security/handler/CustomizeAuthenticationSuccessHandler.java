package com.cyberpeace.auth.security.handler;

import static javax.servlet.http.HttpServletResponse.SC_OK;

import java.io.IOException;
import java.util.Objects;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.cyberpeace.auth.constant.UserEnableEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import com.cyberpeace.auth.domain.SysUser;
import com.cyberpeace.auth.mapper.SysUserMapper;
import com.cyberpeace.auth.security.TbUser;
import com.cyberpeace.auth.util.CommonUtil;
import com.cyberpeace.common.model.BaseRsp;

import lombok.extern.slf4j.Slf4j;

/**
 * 登录成功处理逻辑
 */
@Component
@Slf4j
public class CustomizeAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

	@Autowired
	private SysUserMapper sysUserMapper;

    @Override
	public void onAuthenticationSuccess(HttpServletRequest httpServletRequest,
			HttpServletResponse httpServletResponse, Authentication authentication)
			throws IOException, ServletException {
        //更新用户表上次登录时间、更新人、更新时间等字段
		TbUser userDetails = (TbUser) SecurityContextHolder.getContext().getAuthentication()
				.getPrincipal();
		SysUser sysUser = sysUserMapper.selectByUsername(userDetails.getUsername());
		if (Objects.equals(UserEnableEnum.DISABLED.getCode(), sysUser.getEnabled())){
			throw new LockedException("账号禁用");
		}
        sysUserMapper.updateById(sysUser);


        HttpSession session = httpServletRequest.getSession();
        //返回json数据
        CommonUtil.responseJson(httpServletResponse, new BaseRsp(session.getId()), SC_OK);
    }
}
