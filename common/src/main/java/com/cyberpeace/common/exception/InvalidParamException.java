package com.cyberpeace.common.exception;

/**
 * 无效参数异常
 * 用户输入参数异常
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public class InvalidParamException extends RuntimeException {

	/**
	 * 无效参数异常
	 *
	 * @param message 消息
	 */
	public InvalidParamException(String message) {
		super(message);
	}
}
