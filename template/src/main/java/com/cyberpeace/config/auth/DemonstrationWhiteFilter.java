package com.cyberpeace.config.auth;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.cyberpeace.common.threadlocal.ThreadLocalContext;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.SpringContextUtil;
import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.enums.DemonstrationWhiteUriEnum;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * DemonstrationWhiteFilter类用于实现一个过滤器，主要用于演示环境的白名单过滤
 * 它根据系统属性和远程服务的配置来决定是否允许请求通过
 */
@Slf4j
public class DemonstrationWhiteFilter implements Filter {

    // 从系统属性中获取白名单演示环境的配置
    private String demo = SystemPropUtil.getProp("WHITE_DEMO");
    // 从系统属性中获取缓存使用的配置
    private String cache = SystemPropUtil.getProp("CACHE_OPEN");

    // 标记演示环境是否开启
    private Boolean demoOpen=false;
    // 使用CacheBuilder构建一个缓存，用于存储文件内容，缓存策略为写入后1天过期
    private Cache<String,String> contentCache= CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.DAYS).build();
    // 当前时间戳，用于某些特定的逻辑处理
    private long now= 0L;

    /**
     * doFilter方法是过滤器的核心方法，用于处理每个请求
     * 它根据演示环境的配置和请求的URI来决定是否允许请求通过
     *
     * @param servletRequest Servlet请求对象，用于获取请求的相关信息
     * @param servletResponse Servlet响应对象，用于向客户端发送响应
     * @param filterChain 过滤链对象，用于将请求传递给下一个过滤器或目标资源
     * @throws IOException 当处理请求或响应过程中发生I/O错误时抛出
     * @throws ServletException 当处理请求过程中发生Servlet错误时抛出
     */
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        // 将ServletRequest转换为HttpServletRequest，以便获取更多的请求信息
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        // 通过SpringContextUtil获取RemoteUserService的实例，用于判断演示环境是否开启
        RemoteUserService remoteUserService= SpringContextUtil
                .getBeanByClass(RemoteUserService.class);
        // 更新demoOpen的状态
        demoOpen= remoteUserService.isDemoOpen();
        // 如果演示环境开启或系统属性配置为"open"，则处理白名单请求
        if (Boolean.TRUE.equals(demoOpen)||"open".equals(demo)) {
            dealDemoOpen(request);
        }
        // 继续处理其他请求
        filterChain.doFilter(servletRequest, servletResponse);
        // 请求处理完毕后，设置白名单更新的线程局部变量为false
        ThreadLocalContext.whiteUpdateThreadLocal.set(false);
    }

    /**
     * dealDemoOpen方法用于处理演示环境开启时的白名单请求
     * 它根据请求的URI和预定义的白名单来决定是否允许请求通过
     *
     * @param request HttpServletRequest对象，用于获取请求的URI和方法
     * @throws IOException 当读取文件内容过程中发生I/O错误时抛出
     */
    private void dealDemoOpen(HttpServletRequest request) throws IOException{
        // 获取请求的路径或其他条件，判断是否为名单内请求
        String uri = request.getRequestURI();
        // 获取白名单URI的枚举实例
        DemonstrationWhiteUriEnum demonstrationUriEnum = DemonstrationWhiteUriEnum.WHITE;
        // 从枚举实例中获取文件路径
        String filePath = demonstrationUriEnum.getFilePath();
        // 根据文件路径创建File对象
        File file = new File(filePath);
        // 如果文件不存在，则尝试从系统路径中获取
        if (!file.exists()) {
            file = ResourceUtils.getFile(demonstrationUriEnum.getSystemPath());
        }
        // 如果文件存在，则从缓存中获取内容或读取文件内容并存入缓存
        if (file.exists()) {
            String content ="open".equals(cache)?contentCache.getIfPresent(filePath):null;
            if (content==null) {
                content = FileUtil.getContent(file);
                contentCache.put(filePath,content);
            }
            // 将文件内容解析为JSONArray，并检查请求的URI是否在白名单中
            JSONArray jsonArray = JSON.parseArray(content);
            if (jsonArray.contains(uri)||jsonArray.contains(uri+":"+request.getMethod())) {
                // 如果URI在白名单中，设置白名单更新的线程局部变量为true
                ThreadLocalContext.whiteUpdateThreadLocal.set(true);
            } else {
                // 如果URI不在白名单中，记录日志信息
                log.info("current uri is ====>"+uri);
            }
        }
    }

    /**
     * init方法用于初始化过滤器
     * 目前该方法的实现为空，但可以根据需要添加初始化逻辑
     *
     * @param filterConfig 过滤器配置对象，包含过滤器的配置信息
     * @throws ServletException 当初始化过程中发生错误时抛出
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // 空实现，保留供未来可能的初始化使用
    }

    /**
     * destroy方法用于销毁过滤器
     * 目前该方法的实现为空，但可以根据需要添加清理资源的逻辑
     */
    @Override
    public void destroy() {
        // 空实现，保留供未来可能的清理使用
    }

}

