package com.cyberpeace.config.auth;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.SpringContextUtil;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.rtks.dao.entity.UserRole;
import com.cyberpeace.rtks.service.UserRelateService;
import com.google.common.collect.Lists;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class BasicValidationRealm extends AuthorizingRealm {

	@Autowired
	private UserRelateService userRelateService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

		UserInfo userInfo = UserInfoContext.getUserContext();

		// 设置角色
		simpleAuthorizationInfo.addRoles(userInfo.getRoles());

		// 默认不设置权限
		simpleAuthorizationInfo.addStringPermissions(Lists.newArrayList());

		return simpleAuthorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		UserInfo userInfo = null;

		if (null == authenticationToken.getCredentials()) {
			throw new AuthenticationException();
		}

		String token = new String((char[]) authenticationToken.getCredentials());
		if (StringUtils.isBlank(token)) {
			throw new AuthenticationException();
		}

		try {
			RemoteUserService remoteUserService = SpringContextUtil
					.getBeanByClass(RemoteUserService.class);

			userInfo = remoteUserService.doAuthentication(token);
			UserRole roleInfo = userRelateService.getUserRoleInfo(userInfo.getUserId());

			if (null != roleInfo) {
				userInfo.setRoleId(roleInfo.getRoleId());
			}

			UserInfoContext.setUserContext(userInfo);
		} catch (Exception e) {
			log.error("RemoteUserService.doAuthentication error");
			throw new AuthenticationException();
		}

		return new SimpleAuthenticationInfo(userInfo.getUserId(), token, getName());
	}
}
