package com.cyberpeace.config.auth;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.ConfigUtil;
import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 静态资源过滤器
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see Filter
 * @since 1.0.0
 */
@Slf4j
public class StaticResourceFilter implements Filter {

    /**
     * 获取
     *
     * @see String
     */
    private final String get = "GET";

    /**
     * 源 URI
     *
     * @see String
     */
    private final String sourceUri = "/ojj/cpms/";

    /**
     * JWT 密钥
     *
     * @see String
     */
    private final String JWT_KEY = "cr_jwttoken";

    /**
     * @see String
     */
    private final String redirectUri = "/redirect/resource/";

    /**
     * 中台主机
     *
     * @see String
     */
    private String MP_HOST;

    /**
     * 重定向状态
     *
     * @see Boolean
     */
    private Boolean redirectStatus;

    /**
     * 初始化
     *
     * @param filterConfig 过滤器配置
     * @throws @throws ServletException Servlet 异常
     * @see FilterConfig
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        MP_HOST = "http://" + SystemPropUtil.getProp("MP_HOST");
        redirectStatus = Boolean.parseBoolean(ConfigUtil.getContextProperty("redirectStatus"));
        log.info("静态资源权限过滤开启状态：{}", false);
    }

    /**
     * 做过滤器
     *
     * @param request  请求
     * @param response 响应
     * @param chain    链
     * @throws @throws          IOException io异常
     * @throws ServletException Servlet 异常
     * @see ServletRequest
     * @see ServletResponse
     * @see FilterChain
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        //未校验状态直接放行
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        //获取请求路径
        String requestURI = httpRequest.getRequestURI();
        //仅针对GTE方法下获取静态资源的url进行过滤
        if (httpRequest.getMethod().equals(get) && requestURI.contains(sourceUri)) {
            String element = requestURI.replace(sourceUri, redirectUri);
            String redirectUrl = MP_HOST + element;
            log.info("静态资源源路径{}转发路径：{}", requestURI, element);
            if (redirectStatus) {
                //cookie中获取jwt，jwt不存在是抛出异常
                Cookie[] requestCookies = httpRequest.getCookies();
                String cookie = null;
                if (requestCookies != null && requestCookies.length > 0) {
                    for (Cookie requestCookie : requestCookies) {
                        if (JWT_KEY.equals(requestCookie.getName())) {
                            cookie = requestCookie.getValue();
                        }
                    }
                }
                if (StringUtils.isEmpty(cookie)) {
                    log.error("cookie is null , cookie = [{}]", cookie);
                    throw new BusinessException(CrBusinessErrorCode.PERMISSION_DENIED);
                }
            }
            httpResponse.sendRedirect(redirectUrl);
        }
        chain.doFilter(request, response);
    }

    /**
     * 摧毁
     *
     * @throws
     */
    @Override
    public void destroy() {

    }
}
