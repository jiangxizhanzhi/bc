package com.cyberpeace.common.exception;

/**
 * 资源不发现异常
 * 数据库对应资源失败，抛出此异常
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public class ResourceNotFoundException extends RuntimeException {

	/**
	 * 资源不发现异常
	 *
	 * @param message 假设根据resource_id字段查询数据失败，message=resource_id；
	 *                多个字段联合查询时，此字段为空
	 */
	public ResourceNotFoundException(String message) {
		super(message);
	}
}
