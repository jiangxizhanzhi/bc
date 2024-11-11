package com.cyberpeace.config.auth;

import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.model.CommonContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.impl.TraceIdContext;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class MyFilter implements Filter {

    /**
     * 初始化过滤器
     *
     * @param filterConfig 过滤器配置对象
     * @throws ServletException 如果初始化过程中出现错误
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {}

    /**
     * 执行过滤操作
     *
     * @param request Servlet请求对象
     * @param response Servlet响应对象
     * @param chain 过滤链对象，用于调用下一个过滤器或目标资源
     * @throws IOException 如果在执行过程中发生I/O错误
     * @throws ServletException 如果在执行过程中发生Servlet错误
     */
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        // 1、设置traceId
        setTraceId(request);

        // 记录开始时间，用于计算请求耗时
        long startTime = System.currentTimeMillis();
        // 获取请求的URL路径
        String url = ((HttpServletRequest) request).getServletPath();
        // 继续执行过滤链上的其他过滤器或目标资源
        chain.doFilter(request, response);
        // 记录请求耗时
        log.debug("{}|{}", url, (System.currentTimeMillis() - startTime));
    }

    /**
     * 销毁过滤器
     */
    @Override
    public void destroy() {
        // 什么都不需要做
    }

    /**
     * 设置traceId和语言信息到上下文中
     *
     * @param request Servlet请求对象
     */
    private void setTraceId(ServletRequest request) {
        // 从请求头中获取traceId，如果不存在则生成一个新的traceId
        String traceId = ((HttpServletRequest) request).getHeader(WebConstant.TRACE_HEADER);

        // 从请求头中获取语言信息，如果不存在或不是英文，则默认使用中文
        String language = ((HttpServletRequest) request).getHeader(WebConstant.LANGUAGE);

        if (StringUtils.isBlank(traceId)) {
            traceId = UUID.randomUUID().toString();
        }

        if (!StringUtils.equals(language, WebConstant.LANGUAGE_EN)) {
            language = WebConstant.LANGUAGE_ZH;
        }

        // 将语言信息和客户端IP地址设置到公共上下文中
        CommonContext.setLanguage(language);
        CommonContext.setIp(getIpAddr(request));

        // 将traceId设置到TraceId上下文中
        TraceIdContext.setTraceId(traceId);
    }

    /**
     * 获取客户端IP地址
     *
     * @param servletRequest Servlet请求对象
     * @return 客户端IP地址，如果获取失败则返回"127.0.0.1"
     */
    public String getIpAddr(ServletRequest servletRequest) {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        // 通过请求头获取客户端IP地址
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        // 如果仍然无法获取到IP地址，则默认使用"127.0.0.1"
        if (StringUtils.isBlank(ip)) {
            ip = "127.0.0.1";
        }
        return ip;
    }

}

