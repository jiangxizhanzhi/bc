package com.cyberpeace.oj.common.exception;

/**
 * 用户输入参数异常
 * */
public class InvalidParamException extends RuntimeException {

	public InvalidParamException(String message) {
		super(message);
	}
}
