package com.cyberpeace.common.exception.evaluate;

public class SupersetServiceException extends RuntimeException {

	/**
	 * superset服务异常
	 *
	 * @param message 消息
	 */
	public SupersetServiceException(String message) {
		super(message);
	}

	public SupersetServiceException(Integer code) {
		super("superset服务异常: " + code);
	}

	/**
	 * 远程服务异常
	 */
	public SupersetServiceException() {
		super("superset服务异常：响应为空");
	}

	@Override
	public String getMessage() {
		return super.getMessage();
	}
}
