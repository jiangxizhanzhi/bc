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
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.ResourceUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DemonstrationFilter implements Filter {


    private String demo = SystemPropUtil.getProp("Demo");

    private Cache<String,String> contentCache= CacheBuilder.newBuilder().expireAfterWrite(1, TimeUnit.DAYS).build();
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if ("open".equals(demo)) {
            // 获取请求的路径或其他条件，判断是否为名单内请求
            String uri = request.getRequestURI();
            log.info("request uri=============>"+uri);
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


    @Override
    public void init(javax.servlet.FilterConfig filterConfig) throws ServletException {

    }
    @Override
    public void destroy() {
        // 销毁过滤器
    }

}
