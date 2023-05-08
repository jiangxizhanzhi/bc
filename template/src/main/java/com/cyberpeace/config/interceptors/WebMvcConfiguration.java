package com.cyberpeace.config.interceptors;

import com.cyberpeace.common.util.CustomExceptionHandlerResolver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * web mvc配置
 * 拦截器的配置
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private PracticeGateWayInterceptor practiceGateWayInterceptor;

    /**
     * 添加拦截器
     *
     * @param registry 注册表
     */
    @Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(practiceGateWayInterceptor).addPathPatterns("/gateway/theory/**",
				"/gateway/exercise/**");
	}

    /**
     * 配置处理程序异常解析器
     *
     * @param resolvers 解析器
     */
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> resolvers) {
        // 劫持spring的异常处理
        resolvers.clear();
        resolvers.add(new CustomExceptionHandlerResolver());
    }
}
