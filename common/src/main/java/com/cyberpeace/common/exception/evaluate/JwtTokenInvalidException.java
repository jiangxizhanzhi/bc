package com.cyberpeace.common.exception.evaluate;

/**
 * JWT 令牌无效异常
 *
 * @author SN
 * @date 2024/11/08
 */
public class JwtTokenInvalidException extends RuntimeException {

	public JwtTokenInvalidException(String message) {
		super(message);
	}

	public JwtTokenInvalidException() {
		super();
	}
}
