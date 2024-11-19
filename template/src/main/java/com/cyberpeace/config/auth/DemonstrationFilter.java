package com.cyberpeace.config.auth;

import com.alibaba.fastjson.JSON;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.enums.DemonstrationUriEnum;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.util.ResourceUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 示范过滤器
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see Filter
 * @since 1.0.0
 */
public class DemonstrationFilter implements Filter {


    /**
     * 演示
     *
     * @see String
     */
    private String demo = SystemPropUtil.getProp("Demo");

    /**
     * 内容缓存
     *
     * @see Cache<String, String>
     */
    private Cache<String,String> contentCache= CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.DAYS).build();

    /**
     * 做过滤器
     *
     * @param servletRequest  Servlet 请求
     * @param servletResponse Servlet 响应
     * @param filterChain     过滤器链
     * @throws @throws          IOException io异常
     * @throws ServletException Servlet 异常
     * @see ServletRequest
     * @see ServletResponse
     * @see FilterChain
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if ("open".equals(demo)) {
            // 获取请求的路径或其他条件，判断是否为名单内请求
            String uri = request.getRequestURI();
            DemonstrationUriEnum demonstrationUriEnum = DemonstrationUriEnum.getByUri(uri);
            if (demonstrationUriEnum!=null) {
                String filePath = demonstrationUriEnum.getFilePath();
                File file = new File(filePath);
                if (!file.exists()) {
                    file = ResourceUtils.getFile(demonstrationUriEnum.getSystemPath());
                }
                if (file.exists()) {
                    String content =contentCache.getIfPresent(filePath);
                    if (content==null) {
                        content = FileUtil.getContent(file);
                        if (demonstrationUriEnum.getDemonstrationService()!=null) {
                            content= demonstrationUriEnum.getDemonstrationService().deal(content);
                        }
                        contentCache.put(filePath,content);
                    }
                    BaseRsp<Object> baseRsp = new BaseRsp<>(JSON.parse(content));
                    // 如果是名单内请求，直接返回固定数据
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("text/plain;charset=UTF-8");
                    response.getWriter().write(JSON.toJSONString(baseRsp));
                    return;
                }

            }
        }
        // 继续处理其他请求
        filterChain.doFilter(servletRequest, servletResponse);
    }


    /**
     * 初始化
     *
     * @param filterConfig 过滤器配置
     * @throws @throws ServletException Servlet 异常
     * @see javax.servlet.FilterConfig
     */
    @Override
    public void init(javax.servlet.FilterConfig filterConfig) throws ServletException {

    }

    /**
     * 摧毁
     *
     * @throws
     */
    @Override
    public void destroy() {
        // 销毁过滤器
    }

}
