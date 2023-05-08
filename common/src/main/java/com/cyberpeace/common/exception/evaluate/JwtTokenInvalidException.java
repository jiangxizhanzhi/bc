package com.cyberpeace.common.exception.evaluate;

/**
 * jwt令牌无效异常
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public class JwtTokenInvalidException extends RuntimeException {

	/**
	 * jwt令牌无效异常
	 *
	 * @param message 消息
	 */
	public JwtTokenInvalidException(String message) {
		super(message);
	}

	/**
	 * jwt令牌无效异常
	 */
	public JwtTokenInvalidException() {
		super();
	}
}
