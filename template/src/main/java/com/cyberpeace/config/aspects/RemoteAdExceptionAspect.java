package com.cyberpeace.config.aspects;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.exception.RemoteServiceException;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * 日志记录和异常处理切面类
 * 用于拦截远程广告服务调用过程中的异常
 */
@Slf4j
@Aspect
@Component
public class RemoteAdExceptionAspect {

    /**
     * 定义一个切点，用于匹配远程广告服务中的所有方法执行
     * 这个切点没有直接的逻辑实现，仅用于声明切点表达式
     */
    @Pointcut("execution(* com.cyberpeace.integratation.service.RemoteAdService.*(..))")
    public void operlog(){
    }

    /**
     * 异常抛出后处理方法
     * 在匹配切点的方法执行过程中，如果抛出了异常，则该方法会被调用
     *
     * @param e 异常对象，用于获取异常信息
     * @throws Exception 可能重新抛出的异常
     */
    @AfterThrowing(value = "operlog()", throwing = "e")
    public void afterThrowing(Exception e) throws Exception {
        // 判断异常类型是否为RemoteServiceException
        if (e instanceof RemoteServiceException){
            // 如果异常消息不为空，则直接抛出原异常
            if(null != e.getMessage()){
                throw e;
            } else {
                // 如果异常消息为空，则抛出一个自定义的业务异常
                throw new BusinessException(ErrorCode.ERROR_AD_SERVICE);
            }
        }
    }
}

