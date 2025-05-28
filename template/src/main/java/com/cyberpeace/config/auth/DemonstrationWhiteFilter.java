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


    private String demo = SystemPropUtil.getProp("WHITE_DEMO");
    private String cache = SystemPropUtil.getProp("CACHE_OPEN");

    private Boolean demoOpen=false;
    private Cache<String,String> contentCache= CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.DAYS).build();
    private long now= 0L;





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

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        //
    }
    @Override
    public void destroy() {
        // 销毁过滤器
    }

}
