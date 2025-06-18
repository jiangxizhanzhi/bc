package com.cyberpeace.cs.aspect;

import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.integratation.service.YXLogService;
import com.cyberpeace.integratation.service.YXUserService;
import com.cyberpeace.integratation.service.bo.mp.YXLogRequest;
import com.cyberpeace.integratation.service.bo.mp.YXLogUrlEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

/**
 * ThirdLogAspect
 *
 * @author Jerry(姜源)
 * @since 2025/06/13 17:01
 */

@Aspect
@Component
@Slf4j
public class ThirdLogAspect {

    @Autowired
    private YXLogService yxLogService;

    /**
     * 定义切入点：所有Controller层方法
     */
    @Pointcut("execution(* com.cyberpeace..controller..*.*(..))")
    public void controllerPointcut() {}


    /**
     * 统一处理所有Controller和Service方法
     */
    @Around("controllerPointcut()")
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) throws Throwable {

        // 执行请求并获取请求后相关信息
        Object result = joinPoint.proceed();

        try {
            // 获取入参
            //Object[] args = joinPoint.getArgs();

            // 如果返回的是ResponseEntity，可以从中获取HTTP状态码
            boolean isSuccess = false;
            if (result instanceof org.springframework.http.ResponseEntity) {
                int httpStatus = ((org.springframework.http.ResponseEntity<?>) result).getStatusCodeValue();
                if (HttpStatus.OK.value() == httpStatus){
                    isSuccess = true;
                }
            }


            // 执行日志上报操作
            YXLogRequest request = getYXLogRequest(isSuccess);
            if (null != request){
                yxLogService.uploadLog(request);
            }
        }catch (Exception e){
            // 不处理
        }

        return result;
    }

    // 获取请求信息
    private YXLogRequest getYXLogRequest(boolean isSuccess) {

        // 获取请求相关信息
        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).
                    getRequest();
            // 请求URL
            String requestUrl = request.getRequestURI();
            // 获取HTTP方法
            String httpMethod = request.getMethod();
            // 获取客户端IP地址
            String clientIp = getClientIp(request);
            // 获取请求头
            //Map<String, String> headers = getHeadersInfo(request);

            // 根据URL获取一些操作信息
            // 存在用户信息才进行下一步
            if (null != UserInfoContext.getUserContext()){
                //log.info("user online, userName={}, name={}, userId={}",
                //        UserInfoContext.getUserContext().getUserName(),
                //        UserInfoContext.getUserContext().getName(),
                //        UserInfoContext.getUserContext().getUserId());
                YXLogRequest yxLogRequest = generateYXLogRequestByURL(requestUrl, httpMethod);
                if (null == yxLogRequest){
                    return null;
                }
                log.info("prepare log for yx, requestUrl={}, httpMethod={}, yxLogRequest={}", requestUrl, httpMethod,
                        yxLogRequest.getType() +"--" + yxLogRequest.getAction()+"--"+yxLogRequest.getContent());
                yxLogRequest.setIp(clientIp);
                yxLogRequest.setTime("" + System.currentTimeMillis()/1000);
                yxLogRequest.setResult(isSuccess? "1":"0");
                yxLogRequest.setUsername(UserInfoContext.getUserContext().getName());
                yxLogRequest.setRoleId(UserInfoContext.getUserContext().getRoleId());

                return yxLogRequest;
            }
        }catch (Exception e){
            // 不处理
        }

        return null;
    }

    private YXLogRequest generateYXLogRequestByURL(String requestUrl, String method) {

        YXLogRequest request = new YXLogRequest();
        // 根据请求url获取对应的操作信息及操作类型信息
        YXLogUrlEnum.YXLogInfo logInfo= YXLogUrlEnum.getLogInfo(requestUrl, method);
        if (null == logInfo || null == logInfo.getContent()){
            return null;
        }
        request.setType(logInfo.getType());
        request.setContent(logInfo.getContent());
        request.setAction(logInfo.getAction());
        return request;
    }

    /**
     * 获取客户端真实IP地址
     */
    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    /**
     * 获取请求头信息
     */
    private Map<String, String> getHeadersInfo(HttpServletRequest request) {
        Map<String, String> headers = new HashMap<>();
        Enumeration<String> headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String headerName = headerNames.nextElement();
            headers.put(headerName, request.getHeader(headerName));
        }
        return headers;
    }
}