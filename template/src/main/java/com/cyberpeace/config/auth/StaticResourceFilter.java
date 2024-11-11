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
/**
 * 静态资源过滤器，用于过滤特定的静态资源请求
 */
public class StaticResourceFilter implements Filter {

    // 定义GET请求方法的字符串常量
    private final String get = "GET";

    // 定义源URI的字符串常量
    private final String sourceUri = "/ojj/cpms/";

    // 定义JWT密钥的字符串常量
    private final String JWT_KEY = "cr_jwttoken";

    // 定义重定向URI的字符串常量
    private final String redirectUri = "/redirect/resource/";

    // 存储MP_HOST的URL地址
    private String MP_HOST;

    // 存储重定向状态的布尔变量
    private Boolean redirectStatus;

    /**
     * 初始化过滤器
     *
     * @param filterConfig 过滤器配置对象
     * @throws ServletException 如果初始化过程中出现错误
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 从系统属性中获取MP_HOST的值，并构建完整的URL
        MP_HOST = "http://" + SystemPropUtil.getProp("MP_HOST");
        // 从上下文中获取重定向状态的值，并解析为布尔类型
        redirectStatus = Boolean.parseBoolean(ConfigUtil.getContextProperty("redirectStatus"));
        // 记录静态资源过滤的开启状态
        log.info("静态资源权限过滤开启状态：{}", false);
    }

    /**
     * 执行过滤操作
     *
     * @param request 请求对象
     * @param response 响应对象
     * @param chain 过滤链对象
     * @throws IOException 如果执行过程中发生I/O错误
     * @throws ServletException 如果执行过程中发生Servlet错误
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        // 未校验状态直接放行
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        // 获取请求路径
        String requestURI = httpRequest.getRequestURI();
        // 仅针对GET方法下获取静态资源的URL进行过滤
        if (httpRequest.getMethod().equals(get) && requestURI.contains(sourceUri)) {
            // 构建重定向的URL
            String element = requestURI.replace(sourceUri, redirectUri);
            String redirectUrl = MP_HOST + element;
            // 记录静态资源的源路径和转发路径
            log.info("静态资源源路径{}转发路径：{}", requestURI, element);
            // 如果重定向状态为真，则进行JWT验证
            if (redirectStatus) {
                // 从cookie中获取jwt，如果jwt不存在则抛出异常
                Cookie[] requestCookies = httpRequest.getCookies();
                String cookie = null;
                if (requestCookies != null && requestCookies.length > 0) {
                    for (Cookie requestCookie : requestCookies) {
                        if (JWT_KEY.equals(requestCookie.getName())) {
                            cookie = requestCookie.getValue();
                        }
                    }
                }
                // 如果cookie为空，则记录错误日志并抛出业务异常
                if (StringUtils.isEmpty(cookie)) {
                    log.error("cookie is null , cookie = [{}]", cookie);
                    throw new BusinessException(CrBusinessErrorCode.PERMISSION_DENIED);
                }
            }
            // 重定向到构建的URL
            httpResponse.sendRedirect(redirectUrl);
        }
        // 继续执行过滤链中的其他过滤器
        chain.doFilter(request, response);
    }

    /**
     * 销毁过滤器
     */
    @Override
    public void destroy() {
        // 此处无需实现销毁逻辑
    }
}

