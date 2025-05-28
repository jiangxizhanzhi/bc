/**
 * 这段代码实现了一个名为 `DemonstrationFilter` 的自定义过滤器，用于根据特定条件拦截 HTTP 请求并返回固定内容。以下是代码的主要功能和结构分析： 
 * ### 主要功能： 
 * 1. **演示模式控制**： 
 * - 根据系统属性 `Demo` 的值（如 `"open"`）来决定是否启用演示模式。 
 * - 如果 `Demo` 不等于 `"open"`，则直接调用 `filterChain.doFilter()`，继续处理其他请求。 
 * 2. **请求路径匹配**： 
 * - 获取当前请求的 URI，并通过枚举类 `DemonstrationUriEnum` 判断该路径是否属于演示模式的白名单。 
 * 3. **文件读取与缓存**： 
 * - 如果请求路径在白名单中，则尝试从指定路径加载文件内容。 
 * - 使用 Guava 的 `Cache` 缓存文件内容，避免重复读取文件。 
 * 4. **内容处理与返回**： 
 * - 如果文件存在且内容未被缓存，则读取文件内容并通过 `FileUtil` 工具类获取文件内容。 
 * - 如果存在对应的 `DemonstrationService`，则调用其方法对内容进行处理。 
 * - 将处理后的结果封装到 `BaseRsp` 对象中，并以 JSON 格式返回给客户端。 
 * 5. **过滤器生命周期方法**： 
 * - 实现了 `Filter` 接口的三个方法： 
 * - `doFilter`：核心逻辑，处理请求并决定是否中断过滤器链。 
 * - `init`：初始化方法，目前为空。 
 * - `destroy`：销毁方法，目前为空。 
 * ### 关键点： 
 * - **缓存机制**：使用 Guava 的缓存避免重复读取文件，提升性能。 
 * - **模块化设计**：通过枚举类 `DemonstrationUriEnum` 管理白名单路径及其相关配置（如文件路径和服务逻辑）。 
 * - **响应格式**：返回的内容统一为 JSON 格式，便于前端解析。 
 * ### 总结： 
 * 该过滤器主要用于在演示环境中拦截特定路径的请求，返回固定的模拟数据，同时支持内容缓存和动态处理。这种设计适用于需要快速搭建演示环境或模拟接口的场景。 
**/
package com.cyberpeace.config.auth;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.SystemPropUtil;
import com.cyberpeace.config.filter.FilterConfig;
import com.cyberpeace.enums.DemonstrationUriEnum;
import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import org.springframework.util.ResourceUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

/**
 * 示范过滤器
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
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
                    BaseRsp baseRsp = new BaseRsp(JSONObject.parse(content));
                    // 如果是名单内请求，直接返回固定数据
                    response.setCharacterEncoding("UTF-8");
                    response.setContentType("text/plain;charset=UTF-8");
                    response.getWriter().write(JSONObject.toJSONString(baseRsp));
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
