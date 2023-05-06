package com.cyberpeace.common.exception.evaluate;

/**
 * 资源无效异常
 *
 * @author cyberpeace
 * @date 2023/05/06
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
