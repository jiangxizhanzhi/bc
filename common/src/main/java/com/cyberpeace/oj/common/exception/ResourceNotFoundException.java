package com.cyberpeace.oj.common.exception;

/**
 * 数据库对应资源失败，抛出此异常
 */
public class ResourceNotFoundException extends RuntimeException {

    /**
     * @param message 假设根据resource_id字段查询数据失败，message=resource_id；
     *                多个字段联合查询时，此字段为空
     */
    public ResourceNotFoundException(String message) {
        super(message);
    }
}
