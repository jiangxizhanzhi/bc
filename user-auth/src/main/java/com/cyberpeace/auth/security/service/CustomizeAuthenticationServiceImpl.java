package com.cyberpeace.auth.security.service;

import javax.annotation.Resource;

import com.cyberpeace.auth.security.TbUser;
import com.cyberpeace.common.constant.ErrorCode;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cyberpeace.auth.domain.SysUser;
import com.cyberpeace.auth.mapper.SysUserMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义登录实现类
 */

@Slf4j
@Service
public class CustomizeAuthenticationServiceImpl implements UserDetailsService {

    @Resource
    private SysUserMapper sysUserMapper;

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
            throw new UsernameNotFoundException(ErrorCode.USER_INFO_MISSING);
        }
		return new TbUser(sysUser);
    }
}
