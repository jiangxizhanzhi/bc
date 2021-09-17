package com.cyberpeace.common.exception.evaluate;

public class JwtTokenInvalidException extends RuntimeException {

	public JwtTokenInvalidException(String message) {
		super(message);
	}

	public JwtTokenInvalidException() {
		super();
	}
}
