package com.cyberpeace.common.exception.evaluate;

/**
 * 资源无效异常
 *
 * @author SN
 * @date 2024/11/08
 */
public class ResourceInvalidException extends RuntimeException {

	public ResourceInvalidException(String message) {
		super(message);
	}

	public ResourceInvalidException() {
		super();
	}
}
