package com.cyberpeace.auth.security.service;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cyberpeace.auth.domain.SysUser;
import com.cyberpeace.auth.mapper.SysUserMapper;
import com.cyberpeace.auth.security.TbUser;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.ConfigUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义登录实现类
 */

@Slf4j
@Service
public class CustomizeAuthenticationServiceImpl implements UserDetailsService {

    @Autowired
    private SessionRegistry sessionRegistry;

    @Resource
    private SysUserMapper sysUserMapper;

    private static final Integer MAX_CONCURRENT_SESSIONS = Integer
            .parseInt(ConfigUtil.getContextProperty("user.login.count.max"));

    /**
	 * 自定义登录逻
	 *
	 * @param username 用户名称
	 * @return UserDetails 用户信息实体
	 * @throws UsernameNotFoundException
	 */
    @Override
    public UserDetails loadUserByUsername(String username) throws AuthenticationException {
        if (StringUtils.isBlank(username)) {
            throw new UsernameNotFoundException(ErrorCode.USERNAME_NOT_NULL);
        }
        //根据用户名查询用户
		SysUser sysUser = sysUserMapper.selectByUsername(username);
        if (sysUser == null) {
            throw new UsernameNotFoundException("用户不存在");
        }

        // 判断是否超过最大同时登录用户数
		if (sessionRegistry.getAllPrincipals().size() >= MAX_CONCURRENT_SESSIONS) {
			Boolean isLogin = false;
			for (Object principal : sessionRegistry.getAllPrincipals()) {
				if (principal instanceof TbUser) {
					TbUser user = (TbUser) principal;
					if (username.equals(user.getUsername())) {
						isLogin = true;
					}
				}
			}

			// 当登录总数超过限制，且为新登录用户时，抛出异常
			if (!isLogin) {
				throw new BusinessException("同时登录用户达到最大数量");
			}
		}

		return new TbUser(sysUser);
    }
}
