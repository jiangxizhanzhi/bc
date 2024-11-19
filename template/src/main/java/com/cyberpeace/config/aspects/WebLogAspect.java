package com.cyberpeace.config.aspects;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.cyberpeace.common.model.user.UserInfoContext;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;

/**
 * Web日志方面
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Aspect
@Component
@Slf4j
@Order(1)
public class WebLogAspect {

    /**
     * 对象Mapper
     *
     * @see ObjectMapper
     */
    ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Web日志
     *
     * @throws
     */
    @Pointcut("execution(* com.cyberpeace..*.controller..*.*(..))")
    public void webLog(){
        // Do nothing
    }


    /**
     * Web日志周围
     *
     * @param joinPoint 加入点
     * @return {@code Object }
     * @throws @throws Throwable 可投掷
     * @see ProceedingJoinPoint
     * @see Object
     */
    @Around(value = "webLog()")
    public Object webLogAround(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();

            //获取参数值
            Object[] args = joinPoint.getArgs();
            //获取参数名称
            String[] parameterNames = signature.getParameterNames();
            //参数值和参数名称是顺序是一致的
            Map<String, Object> map = new LinkedHashMap<>();
            for (int i = 0; i < parameterNames.length; i++) {
                // 获取每个参数值,过滤掉参数类型为 HttpServletResponse
                if ((Objects.nonNull(args[i])) && (args[i] instanceof HttpServletResponse)) {
                    continue;
                }
                // 添加到LinkedHashMap中
                map.put(parameterNames[i], args[i]);
            }

            String url = null;
            String methodName = null;

            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                HttpServletRequest request = attributes.getRequest();
                url = request.getRequestURI();
                methodName = request.getMethod();
            }
            String userId = null;
            if (UserInfoContext.getUserContext() != null) {
                userId = UserInfoContext.getUserContext().getUserId();
            }
            String mapData;
            try {
                mapData = objectMapper.writeValueAsString(map);
            } catch (Exception e1) {
                mapData = map.toString();
            }
            log.error("request info:{}|{}|{}|{}", userId, url, methodName, mapData);
            throw e;
        }
    }
}
