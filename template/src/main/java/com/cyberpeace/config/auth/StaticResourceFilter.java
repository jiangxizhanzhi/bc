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
 * @Author xuxz
 * @Date 11/7/2022 上午10:29
 */
@Slf4j
public class StaticResourceFilter implements Filter {

    private final String get = "GET";

    private final String sourceUri = "/ojj/cpms/";

    private final String JWT_KEY = "cr_jwttoken";

    private final String redirectUri = "/redirect/resource/";

    private String MP_HOST;

    private Boolean redirectStatus;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        MP_HOST = "http://" + SystemPropUtil.getProp("MP_HOST");
        redirectStatus = Boolean.parseBoolean(ConfigUtil.getContextProperty("redirectStatus"));
        log.info("静态资源权限过滤开启状态：{}", false);
    }

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

    @Override
    public void destroy() {

    }
}
