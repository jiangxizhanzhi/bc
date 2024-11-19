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
 * 远程竞赛例外方面
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @since 1.0.0
 */
@Slf4j
@Aspect
@Component
public class RemoteAdExceptionAspect {


    /**
     * 操作日志
     *
     * @throws
     */
    @Pointcut("execution(* com.cyberpeace.integratation.service.RemoteAdService.*(..))")
    public void operlog(){
        // Do nothing
    }

    /**
     * 扔完之后
     *
     * @param e e
     * @throws @throws Exception 例外
     * @see Exception
     */
    @AfterThrowing(value = "operlog()", throwing = "e")
    public void afterThrowing(Exception e) throws Exception {
        if (e instanceof RemoteServiceException){
            if(null != e.getMessage()){
                throw e;

            } else {
                throw new BusinessException(ErrorCode.ERROR_AD_SERVICE);
            }
        }
    }


}
