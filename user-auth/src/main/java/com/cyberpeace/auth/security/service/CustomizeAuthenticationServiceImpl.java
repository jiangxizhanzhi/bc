package com.cyberpeace.auth.security.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import com.cyberpeace.auth.constant.AuthConstant;
import com.sun.org.apache.xpath.internal.operations.Bool;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.session.Session;
import org.springframework.session.data.redis.RedisIndexedSessionRepository;
import org.springframework.stereotype.Service;

import com.cyberpeace.auth.constant.AuthSystemPropertyConstant;
import com.cyberpeace.auth.domain.SysUser;
import com.cyberpeace.auth.mapper.SysUserMapper;
import com.cyberpeace.auth.security.TbUser;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.cs.service.SystemPropertyService;

import lombok.extern.slf4j.Slf4j;

/**
 * 自定义登录实现类
 */

@Slf4j
@Service
public class CustomizeAuthenticationServiceImpl implements UserDetailsService {

	@Autowired
	private RedisIndexedSessionRepository sessionRepository;

    @Resource
    private SysUserMapper sysUserMapper;

	@Autowired
	private SystemPropertyService systemPropertyService;

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
		List<String> online = getOnlineUsers();
		if (online.size() >= systemPropertyService
				.getSystemPropertyInt(AuthSystemPropertyConstant.MAX_ONLINE_USER_COUNT, 5)
				&& !online.contains(sysUser.getResourceId())) {
			throw new BusinessException("同时登录用户达到最大数量");
		}
		return new TbUser(sysUser);
    }

	private List<String> getOnlineUsers() {
		List<String> onlineUsers = new ArrayList<>();
		List<String> userIds = sysUserMapper.selectAllIds();
		for (String userId : userIds) {
			Map<String, ? extends Session> map = sessionRepository.findByPrincipalName(userId);
			if (map.isEmpty()) {
				continue;
			}
			for (Session session : map.values()) {
				if (!Boolean.TRUE.equals(session.getAttribute(AuthConstant.EXPIRE_KEY))) {
					onlineUsers.add(userId);
					break;
				}
			}
		}
		return onlineUsers;
	}
}
