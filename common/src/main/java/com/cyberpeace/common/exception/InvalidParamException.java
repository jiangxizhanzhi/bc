package com.cyberpeace.common.exception;

/**
 * 用户输入参数异常
 *
 * @author SN
 * @date 2024/11/08
 */
public class InvalidParamException extends RuntimeException {

	public InvalidParamException(String message) {
		super(message);
	}
}
