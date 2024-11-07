package com.cyberpeace.common.exception;

/**
 * 用户输入参数异常
 *
 * @author SN
 * @date 2024/11/07
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
