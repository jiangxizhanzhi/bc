/**
 * 这段代码定义了一个名为 `BasicValidationRealm` 的类，继承自 Apache Shiro 框架中的 `AuthorizingRealm` 类。它实现了用户身份验证 (`Authentication`) 和权限授权 (`Authorization`) 的逻辑。以下是代码的主要功能和结构分析： 
 * ### 主要功能： 
 * 1. **身份验证 (`doGetAuthenticationInfo`)**: 
 * - 接收一个 `AuthenticationToken` 对象，从中提取用户的认证信息（如 token）。 
 * - 使用远程服务 (`RemoteUserService`) 验证 token 的有效性，并获取用户详细信息 (`UserInfo`)。 
 * - 如果验证失败，则抛出 `AuthenticationException`。 
 * - 成功后，将用户信息存储到上下文中，并返回一个 `SimpleAuthenticationInfo` 对象用于后续处理。 
 * 2. **权限授权 (`doGetAuthorizationInfo`)**: 
 * - 根据当前用户的信息 (`UserInfo`) 构建权限信息。 
 * - 使用 `SimpleAuthorizationInfo` 添加用户拥有的权限（如菜单权限）。 
 * - 注释中提到角色权限 (`roles`) 的设置被注释掉，目前仅使用菜单权限进行权限控制。 
 * ### 关键点： 
 * - **依赖注入**：通过 `SpringContextUtil.getBeanByClass()` 获取 `RemoteUserService` 实例，表明该类依赖于 Spring 容器管理的服务类。 
 * - **上下文管理**：使用了 `CommonContext` 和 `UserInfoContext` 来存储全局上下文信息，例如 token 和用户信息。 
 * - **日志记录**：使用了 `log.error` 记录错误信息，便于排查问题。 
 * ### 总结： 
 * `BasicValidationRealm` 是一个典型的 Shiro 自定义 Realm 实现，用于集成远程服务完成用户认证和授权操作。它结合了 Spring 和 Shiro 的特性，适用于需要分布式认证和动态权限管理的场景。 
**/
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
