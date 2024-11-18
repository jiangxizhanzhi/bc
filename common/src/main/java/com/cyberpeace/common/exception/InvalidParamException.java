package com.cyberpeace.common.exception;

/**
 * 用户输入参数异常
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see RuntimeException
 * @since 1.0.0
 */
public class InvalidParamException extends RuntimeException {

	/**
	 * 无效 param 异常
	 *
	 * @param message 消息
	 */
	public InvalidParamException(String message) {
		super(message);
	}
}
