package com.cyberpeace.common.limit;

import com.google.common.util.concurrent.RateLimiter;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * 拦截器的配置
 */
//@Configuration
//public class WebMvcConfiguration implements WebMvcConfigurer {
//
//    @Autowired
//    private Environment environment;
//
//    @Override
//    public void addInterceptors(InterceptorRegistry registry) {
//        int permitsPerSeconds = Integer.parseInt(Objects.requireNonNull(environment.getProperty("permitsPerSeconds")));
//        int warmupPeriod = Integer.parseInt(Objects.requireNonNull(environment.getProperty("warmupPeriod")));
//
//        InterceptorRegistration interceptorRegistration = registry.addInterceptor(new RateLimiterInterceptor(RateLimiter.create(permitsPerSeconds, warmupPeriod, TimeUnit.SECONDS)));
//
//        interceptorRegistration.addPathPatterns("/*/**");
//    }
//}
