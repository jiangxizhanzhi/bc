package com.cyberpeace.common.exception.evaluate;

/**
 * 资源无效异常
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see RuntimeException
 * @since 1.0.0
 */
public class ResourceInvalidException extends RuntimeException {

	public ResourceInvalidException(String message) {
		super(message);
	}

	public ResourceInvalidException() {
		super();
	}
}
