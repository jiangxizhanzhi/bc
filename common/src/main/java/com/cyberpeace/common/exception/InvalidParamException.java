package com.cyberpeace.common.exception;

/**
 * 用户输入参数异常
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see RuntimeException
 * @since 1.0.0
 */
public class InvalidParamException extends RuntimeException {

	public InvalidParamException(String message) {
		super(message);
	}
}
