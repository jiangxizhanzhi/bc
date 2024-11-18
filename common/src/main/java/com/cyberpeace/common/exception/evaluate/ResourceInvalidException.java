package com.cyberpeace.common.exception.evaluate;

/**
 * 资源无效异常
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see RuntimeException
 * @since 1.0.0
 */
public class ResourceInvalidException extends RuntimeException {

	/**
	 * 资源无效异常
	 *
	 * @param message 消息
	 */
	public ResourceInvalidException(String message) {
		super(message);
	}

	/**
	 * 资源无效异常
	 */
	public ResourceInvalidException() {
		super();
	}
}
