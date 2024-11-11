package com.cyberpeace.config.auth;

import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.SpringContextUtil;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.cyberpeace.rtks.dao.entity.UserRole;
import com.cyberpeace.rtks.service.UserRelateService;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

/**
 * BasicValidationRealm类继承了AuthorizingRealm，用于进行基本的验证操作
 * 主要负责用户身份的验证和权限的授权
 */
@Slf4j
@Service
public class BasicValidationRealm extends AuthorizingRealm {

    // 自动注入UserRelateService，用于处理与用户相关的操作
    @Autowired
    private UserRelateService userRelateService;


    /**
     * 根据用户信息获取授权信息
     * 该方法用于获取当前用户的权限信息，包括角色和权限字符串
     *
     * @param principalCollection 用户身份信息集合，用于获取用户身份信息
     * @return AuthorizationInfo 授权信息对象，包含用户的角色和权限信息
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        // 创建一个简单的授权信息对象，用于存储用户的授权信息
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        // 获取当前用户的信息
        UserInfo userInfo = UserInfoContext.getUserContext();

        // 设置角色 todo roles非string
        // simpleAuthorizationInfo.addRoles(userInfo.getRoles());

        // 以菜单作为权限控制
        simpleAuthorizationInfo.addStringPermissions(userInfo.getPermissions());

        // 返回用户的授权信息
        return simpleAuthorizationInfo;
    }

    /**
     * 重写doGetAuthenticationInfo方法以实现自定义的认证逻辑
     * 该方法根据提供的认证令牌获取用户的认证信息
     *
     * @param authenticationToken 用户提供的认证令牌，包含用户凭证
     * @return 返回AuthenticationInfo对象，包含用户的主要认证信息
     * @throws AuthenticationException 如果认证失败或出现异常
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
            throws AuthenticationException {
        UserInfo userInfo = null;

        // 检查认证令牌中的凭证是否为空，如果为空则抛出认证异常
        if (null == authenticationToken.getCredentials()) {
            throw new AuthenticationException();
        }

        // 将认证令牌中的凭证转换为字符串形式的token
        String token = new String((char[]) authenticationToken.getCredentials());
        // 如果token为空，则抛出认证异常
        if (StringUtils.isBlank(token)) {
            throw new AuthenticationException();
        }

        try {
            // 通过Spring上下文工具类获取RemoteUserService的实例
            RemoteUserService remoteUserService = SpringContextUtil
                    .getBeanByClass(RemoteUserService.class);

            // 调用远程用户服务进行用户认证
            userInfo = remoteUserService.doAuthentication(token);
            // 设置公共上下文中的授权信息
            CommonContext.setAuthorization(token); // 调用竞赛接口时需要token
            // 获取用户的权限集合
            Set<String> userPermission = remoteUserService.getUserPermission(userInfo.getUserId());
            userInfo.setPermissions(userPermission);

            // 获取用户的角色信息
            UserRole roleInfo = userRelateService.getUserRoleInfo(userInfo.getUserId());

            // 如果角色信息不为空，则设置用户信息中的角色ID和角色名称
            if (null != roleInfo) {
                userInfo.setRoleId(roleInfo.getRoleId());
                userInfo.setRoleName(roleInfo.getRoleName());
            }

            // 将用户信息设置到用户上下文中
            UserInfoContext.setUserContext(userInfo);
        } catch (Exception e) {
            // 记录认证过程中的异常信息
            log.error("RemoteUserService.doAuthentication error", e);
            // 抛出认证异常
            throw new AuthenticationException();
        }

        // 返回包含用户主要认证信息的SimpleAuthenticationInfo对象
        return new SimpleAuthenticationInfo(userInfo.getUserId(), token, getName());
    }
}
