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
/**
 * ### 代码分析 
 * #### 功能概述 
 * 该代码实现了一个名为 `StaticResourceFilter` 的过滤器，用于拦截 HTTP 请求并对静态资源访问进行权限控制。它主要用于处理与 `/ojj/cpms/` 相关的 GET 请求，并根据配置决定是否需要重定向以及是否允许访问。 
 * --- 
 * #### 主要功能模块 
 * 1. **初始化 (`init` 方法)** 
 * - 在过滤器启动时加载配置： 
 * - 从系统属性中读取 `MP_HOST`（中台主机地址）。 
 * - 从配置文件中读取 `redirectStatus`，决定是否启用重定向功能。 
 * - 记录日志表明过滤器已开启。 
 * 2. **过滤逻辑 (`doFilter` 方法)** 
 * - 接收 `HttpServletRequest` 和 `HttpServletResponse` 对象。 
 * - 判断当前请求是否为 GET 方法且请求路径包含 `/ojj/cpms/`： 
 * - 如果符合条件，将请求路径替换为 `/redirect/resource/` 并拼接中台主机地址生成重定向 URL。 
 * - 如果 `redirectStatus` 为 `true`： 
 * - 检查请求的 Cookie 是否包含指定的 JWT 密钥（`cr_jwttoken`）。 
 * - 如果 JWT 不存在，则抛出权限拒绝异常 (`BusinessException`)。 
 * - 最终通过 `httpResponse.sendRedirect()` 实现重定向。 
 * - 调用 `chain.doFilter()` 放行其他请求。 
 * 3. **销毁 (`destroy` 方法)** 
 * - 当过滤器被销毁时，没有具体操作。 
 * --- 
 * #### 关键点总结 
 * - **权限控制**：通过检查 Cookie 中是否存在 JWT 来判断用户是否有访问权限。 
 * - **动态配置**：支持通过配置文件动态调整是否启用重定向功能。 
 * - **日志记录**：使用 `log.info` 和 `log.error` 记录关键操作和错误信息。 
 * - **安全性**：在未找到 JWT 时抛出业务异常，避免未授权访问。 
 * --- 
 * #### 可能的改进点 
 * 1. **日志记录优化**： 
 * - 当前日志记录较为简单，可以增加更多上下文信息，例如用户 IP 或请求详细信息。 
 * 2. **异常处理**： 
 * - 当前仅捕获了 `IOException` 和 `ServletException`，建议添加更全面的异常处理机制。 
 * 3. **代码复用性**： 
 * - JWT 校验逻辑可以封装为独立方法，提高代码复用性。 
 * 4. **注释完善**： 
 * - 当前注释较简略，可进一步补充每个变量、方法的具体用途及逻辑细节。 
 * --- 
 * #### 总结 
 * 该代码实现了基于 JWT 的静态资源访问权限控制，具有良好的扩展性和日志记录能力。通过合理的配置和动态调整，能够满足大部分场景下的需求。 
**/
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

