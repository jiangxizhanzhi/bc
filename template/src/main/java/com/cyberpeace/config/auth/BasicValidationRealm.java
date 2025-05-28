package com.cyberpeace.config.auth;

import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.SpringContextUtil;
import com.cyberpeace.integratation.service.RemoteUserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * 基本验证领域
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see AuthorizingRealm
 * @since 1.0.0
 */
@Slf4j
@Service
public class BasicValidationRealm extends AuthorizingRealm {

    /**
     * 做获取授权信息
     *
     * @param principalCollection 主体集合
     * @return 授权信息
     * @throws
     * @see PrincipalCollection
     * @see AuthorizationInfo
     */
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

    /**
     * 做获取认证信息
     *
     * @param authenticationToken 鉴权令牌
     * @return 身份验证信息
     * @throws @throws AuthenticationException 鉴权异常
     * @see AuthenticationToken
     * @see AuthenticationInfo
     */
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
            CommonContext.setAuthorization(token); // 调用竞赛接口时需要token
            Set<String> userPermission = remoteUserService.getUserPermission(userInfo.getUserId());
            userInfo.setPermissions(userPermission);


            UserInfoContext.setUserContext(userInfo);

            String cyberToken = remoteUserService.getCyberToken();
            CommonContext.setCyberToken(cyberToken);

        } catch (Exception e) {
            log.error("RemoteUserService.doAuthentication error", e);
            throw new AuthenticationException();
        }

        return new SimpleAuthenticationInfo(userInfo.getUserId(), token, getName());
    }
}
