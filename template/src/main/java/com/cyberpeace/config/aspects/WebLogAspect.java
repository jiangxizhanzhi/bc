package com.cyberpeace.config.aspects;

import com.cyberpeace.common.model.user.UserInfoContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

/**
 * AOP切面类 for logging web requests
 * This class uses AspectJ programming to intercept the execution of controller methods,
 * in order to record information about web requests for tracing and auditing purposes.
 *
 * Why use AOP for logging:
 * Using AOP to log can separate the logging concern from the business logic,
 * following the principle of single responsibility, making the code cleaner and more maintainable.
 * By using AOP, logging functionality can be added or removed without modifying the existing code,
 * enhancing the flexibility and maintainability of the system.
 *
 * Why log web requests:
 * Logging web requests can help trace the execution process of requests,
 * which is crucial for troubleshooting, performance monitoring, and security auditing.
 * It provides a basis for decision-making for system optimization and problem solving.
 */
@Aspect
@Component
@Slf4j
public class WebLogAspect {

    /**
     * ObjectMapper instance for serializing and deserializing JSON
     * This instance is used to handle JSON data in web requests and responses,
     * such as logging request parameters or response results.
     */
    ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Defines a join point for intercepting controller method executions
     * This method does not have an implementation, its main purpose is to define a cutting point,
     * specifying which methods to intercept.
     *
     * Why define a cutting point this way:
     * By defining a cutting expression, it is clearly specified which methods need to be intercepted,
     * making the AOP logic more targeted and efficient.
     * Intercepting controller methods can comprehensively capture information about web requests,
     * providing a basis for subsequent logging.
     */
    @Pointcut("execution(* com.cyberpeace..*.controller..*.*(..))")
    public void webLog(){}



    /**
     * 环绕通知方法，用于拦截控制器的执行，进行日志记录
     * 该方法主要记录请求信息，包括用户ID、请求URL、请求方法和请求参数
     * 在出现异常时，将记录错误日志，并重新抛出异常
     *
     * @param joinPoint 切入点对象，提供了关于当前执行的方法的信息
     * @return 执行链中下一个方法的执行结果
     * @throws Throwable 如果执行过程中出现异常
     */
    @Around(value = "webLog()")
    public Object webLogAround(ProceedingJoinPoint joinPoint) throws Throwable {
        try {
            // 执行下一个方法，即当前被拦截的方法
            return joinPoint.proceed();
        } catch (Exception e) {
            // 获取方法签名
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();

            // 获取参数值
            Object[] args = joinPoint.getArgs();
            // 获取参数名称
            String[] parameterNames = signature.getParameterNames();
            // 参数值和参数名称是顺序是一致的
            Map<String, Object> map = new LinkedHashMap<>();
            for (int i = 0; i < parameterNames.length; i++) {
                // 获取每个参数值
                if (Objects.nonNull(args[i])) {
                    // 过滤掉参数类型为 HttpServletResponse
                    if (args[i] instanceof HttpServletResponse) {
                        continue;
                    }
                }
                // 添加到LinkedHashMap中
                map.put(parameterNames[i], args[i]);
            }

            // 初始化请求URL和方法名
            String url = null;
            String methodName = null;

            // 获取当前请求的属性
            ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
            if (attributes != null) {
                // 获取HTTP请求对象
                HttpServletRequest request = attributes.getRequest();
                // 获取请求的URL
                url = request.getRequestURI();
                // 获取请求的方法名
                methodName = request.getMethod();
            }

            // 初始化用户ID
            String userId = null;
            // 获取当前用户的上下文信息
            if (UserInfoContext.getUserContext() != null) {
                // 获取用户ID
                userId = UserInfoContext.getUserContext().getUserId();
            }

            // 将参数Map转换为JSON字符串
            String mapData;
            try {
                mapData = objectMapper.writeValueAsString(map);
            } catch (Exception e1) {
                // 如果转换失败，则使用Map的toString方法
                mapData = map.toString();
            }

            // 记录错误日志，包括用户ID、请求URL、请求方法和请求参数
            log.error("request info:{}|{}|{}|{}", userId, url, methodName, mapData);
            // 重新抛出异常
            throw e;
        }
    }
}
