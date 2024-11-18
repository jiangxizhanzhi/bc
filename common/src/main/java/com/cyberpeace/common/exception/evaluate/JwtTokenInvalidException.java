package com.cyberpeace.common.exception.evaluate;

/**
 * JWT 令牌无效异常
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @see RuntimeException
 * @since 1.0.0
 */
public class JwtTokenInvalidException extends RuntimeException {

	/**
	 * JWT 令牌无效异常
	 *
	 * @param message 消息
	 */
	public JwtTokenInvalidException(String message) {
		super(message);
	}

	/**
	 * JWT 令牌无效异常
	 */
	public JwtTokenInvalidException() {
		super();
	}
}
