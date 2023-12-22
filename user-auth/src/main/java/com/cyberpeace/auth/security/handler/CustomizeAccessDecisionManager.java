package com.cyberpeace.auth.security.handler;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.cyberpeace.auth.constant.UserEnableEnum;
import com.cyberpeace.auth.domain.SysUser;
import com.cyberpeace.auth.security.TbUser;
import com.cyberpeace.auth.service.SysUserService;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.Objects;

/**
 * @author :   SN
 * @date :   2023/12/18 17:33
 * @desc :
 **/
@Slf4j
@Component
public class CustomizeAccessDecisionManager implements AccessDecisionManager {

    @Autowired
    private SysUserService sysUserService;

    @Override
    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
        HttpServletRequest request = ((FilterInvocation) o).getRequest();
        String requestURI = request.getRequestURI();
        Iterator<ConfigAttribute> iterator = collection.iterator();
        while (iterator.hasNext()){
            ConfigAttribute configAttribute = iterator.next();
            //白名单中的用户，不需要登录就可以访问
            if (StringUtils.equals("permitAll", configAttribute.toString())){
                // 白名单中的接口需要校验可以再此处配置
                return;
            }else {
                //不是白名单中的接口，均需要登录认证后，再判断登录用户是否有权限访问接口
                if (authentication instanceof AnonymousAuthenticationToken){
                    log.error("匿名用户拒绝访问资源：{}", requestURI);
                    throw new DisabledException("账号不可用");
                }
                TbUser principal = (TbUser) authentication.getPrincipal();
                //当前请求需要的权限
                SysUser user = sysUserService.getOne(new LambdaQueryWrapper<SysUser>().eq(SysUser::getUsername, principal.getUsername()));
                if (user != null){
                    //判断当前用户是否已锁定
                    if (Objects.equals(UserEnableEnum.DISABLED.getCode(), user.getEnabled())){
                        throw new LockedException(ErrorCode.USER_DISABLED);
                    }
                    if (user.getExpireTime() != null){
                        if (user.getExpireTime().before(new Date())){
                            throw new AccountExpiredException(ErrorCode.USER_EXPIRE);
                        }
                    }
                }else {
                    throw new UsernameNotFoundException("用户不存在");
                }
                return;
            }
        }
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
