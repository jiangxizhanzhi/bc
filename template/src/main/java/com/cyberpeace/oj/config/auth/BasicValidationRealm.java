package com.cyberpeace.oj.config.auth;

import com.cyberpeace.oj.integratation.service.RemoteUserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.cyberpeace.oj.common.exception.RemoteUserAuthticationException;
import com.cyberpeace.oj.common.model.UserInfo;
import com.cyberpeace.oj.common.model.UserInfoContext;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class BasicValidationRealm extends AuthorizingRealm {

	@Autowired
	private RemoteUserService remoteUserService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

		// 设置角色
		simpleAuthorizationInfo.addRoles(UserInfoContext.getUserContext().getRoles());

		// 默认不设置权限
		simpleAuthorizationInfo.addStringPermissions(Lists.newArrayList());

		return simpleAuthorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		UserInfo userInfo = null;

		try {
			userInfo = remoteUserService
					.doAuthentication(authenticationToken.getPrincipal().toString());

			UserInfoContext.setUserContext(userInfo);
		} catch (RemoteUserAuthticationException e) {
			log.error("RemoteUserService.doAuthentication error");
			throw new AuthenticationException();
		}

		return new SimpleAuthenticationInfo(userInfo.getUserId(), userInfo.getUserId(), getName());
	}
}
