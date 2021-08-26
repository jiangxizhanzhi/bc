package com.cyberpeace.oj.integratation.service.mp;

public class HttpClientException extends RuntimeException {
	public HttpClientException(String message) {
		super(message);
	}

	public HttpClientException(String message, Throwable cause) {
		super(message, cause);
	}
}
