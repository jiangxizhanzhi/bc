package com.cyberpeace.config.auth;

import com.cyberpeace.common.constant.WebConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;
import org.springframework.http.HttpStatus;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * JWT认证过滤器，继承自BasicHttpAuthenticationFilter，用于实现基于JWT的用户认证和授权
 */
@Slf4j
public class JwtFilter extends BasicHttpAuthenticationFilter {

    /**
     * 设置认证方案为JWT
     * @param authzScheme 认证方案名称
     */
    @Override
    public void setAuthzScheme(String authzScheme) {
        super.setAuthzScheme("JWT");
    }

    /**
     * 获取主体和凭证信息
     * 此方法重写默认实现，将JWT token的解码工作交由自定义Realm处理
     * @param scheme 认证方案名称
     * @param realJwt 实际的JWT token
     * @return 包含主体和凭证信息的字符串数组
     */
    @Override
    protected String[] getPrincipalsAndCredentials(String scheme, String realJwt) {
        return new String[] { "", realJwt };
    }

    /**
     * 执行登录操作
     * 该方法重写默认实现，将登录操作提交给自定义Realm处理
     * @param request Servlet请求对象
     * @param response Servlet响应对象
     * @return 登录结果，成功返回true，否则返回false
     * @throws Exception 如果登录失败，则抛出异常
     */
    @Override
    protected boolean executeLogin(ServletRequest request, ServletResponse response)
            throws Exception {
        getSubject(request, response).login(createToken(request, response));
        return true;
    }

    /**
     * 判断是否允许访问
     * 如果是登录尝试，则执行登录操作；如果登录失败，则记录日志并返回401状态码
     * @param request Servlet请求对象
     * @param response Servlet响应对象
     * @param mappedValue 映射值
     * @return 是否允许访问，允许返回true，否则返回false
     */
    @Override
    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response,
            Object mappedValue) {
        if (isLoginAttempt(request, response)) {
            try {
                executeLogin(request, response);
            } catch (Exception e) {
                log.error("user login failed, token={}",
                        ((HttpServletRequest) request).getHeader(WebConstant.AUTHORIZATION_HEADER));
                ((HttpServletResponse) response).setStatus(HttpStatus.UNAUTHORIZED.value());
            }
        }
        return true;
    }

    /**
     * 判断是否是登录尝试
     * 重写默认实现，用于判断当前请求是否是登录尝试
     * @param request Servlet请求对象
     * @param response Servlet响应对象
     * @return 是否是登录尝试，是返回true，否则返回false
     */
    @Override
    protected boolean isLoginAttempt(ServletRequest request, ServletResponse response) {
        return super.isLoginAttempt(request, response);
    }
}
