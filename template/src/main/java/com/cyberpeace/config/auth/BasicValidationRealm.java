package com.cyberpeace.config.auth;

import java.util.Set;

import com.cyberpeace.cr.crbase.service.context.role.UserRole;
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

import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.SpringContextUtil;
import com.cyberpeace.integratation.service.RemoteUserService;

import lombok.extern.slf4j.Slf4j;

/**
 * 基本验证
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Slf4j
@Service
public class BasicValidationRealm extends AuthorizingRealm {

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        UserInfo userInfo = UserInfoContext.getUserContext();

        // 设置角色 todo roles非string
        // simpleAuthorizationInfo.addRoles(userInfo.getRoles());

        // 以菜单作为权限控制
        simpleAuthorizationInfo.addStringPermissions(userInfo.getPermissions());

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
            CommonContext.setAuthorization(token);
            Set<String> userPermission = remoteUserService.getUserPermission(userInfo.getUserId());
            userInfo.setPermissions(userPermission);
            UserInfoContext.setUserContext(userInfo);
        } catch (Exception e) {
            log.error("RemoteUserService.doAuthentication error", e);
            throw new AuthenticationException();
        }

        return new SimpleAuthenticationInfo(userInfo.getUserId(), token, getName());
    }
}
