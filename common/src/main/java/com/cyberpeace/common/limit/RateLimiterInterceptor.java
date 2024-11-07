
//package com.cyberpeace.common.limit;
//
//import com.cyberpeace.common.constant.ErrorCode;
//import com.cyberpeace.common.exception.BusinessException;
//import com.google.common.util.concurrent.RateLimiter;
//import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///** 负责实现限速逻辑
// */
//public class RateLimiterInterceptor extends HandlerInterceptorAdapter {
//
//    private final RateLimiter rateLimiter;
//
//    /**
//     * 通过构造函数初始化限速器
//     */
//    public RateLimiterInterceptor(RateLimiter rateLimiter) {
//        super();
//        this.rateLimiter = rateLimiter;
//    }
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
//        //② 尝试获取1个令牌，不会阻塞当前线程。 立即返回是否获取成功。
//        if(this.rateLimiter.tryAcquire()) {
//            /**
//             * 成功获取到令牌
//             */
//            return true;
//        }
//
//        /**
//         * 获取失败，直接响应“错误信息”
//         * 也可以通过抛出异常，通过全全局异常处理器响应客户端
//         */
//        throw new BusinessException(ErrorCode.INTERFACE_CURRENT_LIMITING);
//
//    }
//}
