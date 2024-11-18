package com.cyberpeace.common.exception;

/**
 * 数据库对应资源失败，抛出此异常
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see RuntimeException
 * @since 1.0.0
 */
public class ResourceNotFoundException extends RuntimeException {

	/**
	 * 未找到资源异常
	 *
	 * @param message 假设根据resource_id字段查询数据失败，message=resource_id；
	 */
	public ResourceNotFoundException(String message) {
		super(message);
	}
}
