package com.cyberpeace.config.auth;

import com.alibaba.fastjson.JSONObject;
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
 * DemonstrationFilter类实现了Filter接口，用于在请求处理前进行演示环境的特殊处理
 * 它根据系统属性中的Demo标志决定是否对特定请求返回预定义的响应内容
 */
public class DemonstrationFilter implements Filter {

    // 从系统属性中获取Demo标志，用于判断是否开启演示模式
    private String demo = SystemPropUtil.getProp("Demo");

    // 使用CacheBuilder构建一个缓存，用于存储文件内容，以提高性能
    private Cache<String,String> contentCache= CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.DAYS).build();

    /**
     * doFilter方法是Filter接口的核心方法，用于执行过滤逻辑
     * 该方法检查Demo标志是否为"open"，如果是，则对特定请求返回预定义的响应内容
     * 如果Demo标志不为"open"，则请求被传递到链中的下一个过滤器或目标资源
     *
     * @param servletRequest Servlet请求对象，用于获取请求信息
     * @param servletResponse Servlet响应对象，用于发送响应信息
     * @param filterChain 过滤链，用于将请求传递到链中的下一个过滤器或目标资源
     * @throws IOException 如果在执行过程中发生I/O错误
     * @throws ServletException 如果在执行过程中发生Servlet错误
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 将ServletRequest和ServletResponse转换为更具体的类型
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        // 检查Demo标志是否为"open"
        if ("open".equals(demo)) {
            // 获取请求的路径或其他条件，判断是否为名单内请求
            String uri = request.getRequestURI();
            DemonstrationUriEnum demonstrationUriEnum = DemonstrationUriEnum.getByUri(uri);
            // 如果是名单内请求，准备返回预定义的响应内容
            if (demonstrationUriEnum!=null) {
                String filePath = demonstrationUriEnum.getFilePath();
                File file = new File(filePath);
                // 如果文件不存在，尝试从系统路径获取
                if (!file.exists()) {
                    file = ResourceUtils.getFile(demonstrationUriEnum.getSystemPath());
                }
                // 如果文件存在，从缓存中获取内容或读取文件内容并缓存
                if (file.exists()) {
                    String content =contentCache.getIfPresent(filePath);
                    if (content==null) {
                        content = FileUtil.getContent(file);
                        // 如果有指定的处理服务，使用该服务处理内容
                        if (demonstrationUriEnum.getDemonstrationService()!=null) {
                            content= demonstrationUriEnum.getDemonstrationService().deal(content);
                        }
                        contentCache.put(filePath,content);
                    }
                    BaseRsp baseRsp = new BaseRsp(JSONObject.parse(content));
                    // 如果是名单内请求，直接返回固定数据
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("text/plain;charset=UTF-8");
                    response.getWriter().write(JSONObject.toJSONString(baseRsp));
                    return;
                }

            }
        }
        // 如果Demo标志不为"open"或请求不是名单内请求，继续处理其他请求
        filterChain.doFilter(servletRequest, servletResponse);
    }

    /**
     * init方法是Filter接口的一部分，用于初始化过滤器
     * 该示例中未实现任何初始化逻辑
     *
     * @param filterConfig 用于访问过滤器的配置信息
     * @throws ServletException 如果初始化过程中发生错误
     */
    @Override
    public void init(javax.servlet.FilterConfig filterConfig) throws ServletException {

    }

    /**
     * destroy方法是Filter接口的一部分，用于在过滤器被移除前执行清理工作
     * 该示例中未实现任何清理逻辑
     */
    @Override
    public void destroy() {
        // 销毁过滤器
    }

}

