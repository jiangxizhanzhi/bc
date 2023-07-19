package com.cyberpeace.common.exception;

/**
 * 远程LcService异常
 *
 * @author SN
 * @date 2023/07/19
 */
public class LcServiceException extends RuntimeException {

    /**
     * 远程服务异常
     *
     * @param message 消息
     */
    public LcServiceException(String message) {
        super(message);
    }

    public LcServiceException(Integer code) {
        super("服务异常: " + code);
    }

    /**
     * 远程服务异常
     */
    public LcServiceException() {
        super("服务异常：响应为空");
    }

    @Override
    public String getMessage() {
        String msg = super.getMessage();
        if ("暂不支持的文件格式".equals(msg)) {
            return "内容识别错误，导入失败";
        } else {
            return msg;
        }
    }
}