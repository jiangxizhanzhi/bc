/**
 * ### 代码分析 
 * #### 功能概述 
 * 该代码实现了一个基于演示模式（Demonstration Mode）的过滤器 `DemonstrationWhiteFilter`。其主要功能是根据系统配置和请求路径，决定是否允许某些特定请求通过，并支持缓存机制以优化性能。 
 * --- 
 * #### 核心功能模块 
 * 1. **配置读取** 
 * - 使用 `SystemPropUtil.getProp` 方法从系统属性中获取两个配置值： 
 * - `WHITE_DEMO`：控制演示模式是否开启。 
 * - `CACHE_OPEN`：控制内容缓存是否启用。 
 * 2. **演示模式开关** 
 * - `demoOpen`：通过调用 `RemoteUserService.isDemoOpen()` 判断演示模式是否开启。 
 * - 如果演示模式开启 (`demoOpen == true`) 或配置中明确设置 `"open".equals(demo)`，则进入演示模式逻辑。 
 * 3. **请求过滤** 
 * - 在 `doFilter` 方法中，检查当前请求路径是否在白名单中： 
 * - 白名单文件路径由枚举类 `DemonstrationWhiteUriEnum` 提供。 
 * - 文件内容通过 `FileUtil.getContent` 方法读取并解析为 JSON 数组。 
 * - 如果请求路径或路径+HTTP 方法匹配白名单中的条目，则设置线程本地变量 `ThreadLocalContext.whiteUpdateThreadLocal.set(true)`，表示当前请求被允许。 
 * 4. **缓存机制** 
 * - 使用 Guava 的 `CacheBuilder` 构建一个缓存对象 `contentCache`，用于存储文件内容。 
 * - 如果缓存未命中且 `cache="open"`，将文件内容读取后存入缓存。 
 * 5. **线程本地变量更新** 
 * - 在过滤器执行完毕后，将 `ThreadLocalContext.whiteUpdateThreadLocal` 设置为 `false`。 
 * --- 
 * #### 关键点总结 
 * - **演示模式**：通过远程服务和配置动态控制是否启用。 
 * - **白名单管理**：通过文件内容定义白名单，并支持动态加载和缓存。 
 * - **线程安全**：使用 `ThreadLocal` 确保上下文信息在多线程环境下的独立性。 
 * - **缓存优化**：通过 Guava 缓存减少文件读取次数，提升性能。 
 * --- 
 * #### 可能存在的问题或改进建议 
 * 1. **异常处理** 
 * - 当前代码在文件操作时未提供详细的异常处理，可能导致运行时错误。 
 * - 建议增加 `try-catch` 块捕获潜在的 `IOException`。 
 * 2. **线程安全** 
 * - 虽然使用了 `ThreadLocal`，但在多实例部署场景下可能需要进一步验证线程安全性。 
 * 3. **日志记录** 
 * - 当前仅在非白名单请求时记录日志，建议对所有关键步骤增加日志输出，便于调试和监控。 
 * 4. **配置灵活性** 
 * - 配置项 `WHITE_DEMO` 和 `CACHE_OPEN` 应考虑更灵活的配置方式，例如支持多个白名单文件或动态更新。 
 * --- 
 * #### 总结 
 * 该代码实现了一个功能完善的演示模式过滤器，结合了配置管理、文件操作、缓存机制和线程本地变量等技术，能够有效满足演示环境的需求。但需要注意增强异常处理和日志记录，以提高代码的健壮性和可维护性。 
**/
package com.cyberpeace.config.auth;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSONArray;
import com.cyberpeace.common.threadlocal.ThreadLocalContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.SpringContextUtil;
import com.cyberpeace.enums.DemonstrationWhiteUriEnum;
import com.cyberpeace.integratation.service.RemoteUserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;

import com.alibaba.fastjson.JSON;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.SystemPropUtil;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

/**
 * 示范白过滤器
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @see Filter
 * @since 1.0.0
 */
@Slf4j
public class DemonstrationWhiteFilter implements Filter {


    /**
     * 演示
     *
     * @see String
     */
    private String demo = SystemPropUtil.getProp("WHITE_DEMO");
    /**
     * 缓存
     *
     * @see String
     */
    private String cache = SystemPropUtil.getProp("CACHE_OPEN");

    /**
     * 演示开启
     *
     * @see Boolean
     */
    private Boolean demoOpen=false;
    /**
     * 内容缓存
     *
     * @see Cache<String, String>
     */
    private Cache<String,String> contentCache= CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.DAYS).build();
    /**
     * 现在
     */
    private long now= 0L;


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
        RemoteUserService remoteUserService= SpringContextUtil
                .getBeanByClass(RemoteUserService.class);
        demoOpen= remoteUserService.isDemoOpen();
        if (Boolean.TRUE.equals(demoOpen)||"open".equals(demo)) {
            dealDemoOpen(request);
        }
        // 继续处理其他请求
        filterChain.doFilter(servletRequest, servletResponse);
        ThreadLocalContext.whiteUpdateThreadLocal.set(false);
    }

    /**
     * 交易演示开启
     *
     * @param request 请求
     * @throws @throws IOException io异常
     * @see HttpServletRequest
     */
    private void dealDemoOpen(HttpServletRequest request) throws IOException{
        // 获取请求的路径或其他条件，判断是否为名单内请求
        String uri = request.getRequestURI();
        DemonstrationWhiteUriEnum demonstrationUriEnum = DemonstrationWhiteUriEnum.WHITE;
        String filePath = demonstrationUriEnum.getFilePath();
        File file = new File(filePath);
        if (!file.exists()) {
            file = ResourceUtils.getFile(demonstrationUriEnum.getSystemPath());
        }
        if (file.exists()) {
            String content ="open".equals(cache)?contentCache.getIfPresent(filePath):null;
            if (content==null) {
                content = FileUtil.getContent(file);
                contentCache.put(filePath,content);
            }
            JSONArray jsonArray = JSON.parseArray(content);
            if (jsonArray.contains(uri)||jsonArray.contains(uri+":"+request.getMethod())) {
                ThreadLocalContext.whiteUpdateThreadLocal.set(true);
            } else {
                log.info("current uri is ====>"+uri);

            }
        }
    }

    /**
     * 初始化
     *
     * @param filterConfig 过滤器配置
     * @throws @throws ServletException Servlet 异常
     * @see FilterConfig
     */
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //
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
