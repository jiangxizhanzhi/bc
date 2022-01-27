package com.cyberpeace.common.exception;

/**
 * @author yinqingrong
 * 调用mp的服务出错，抛出此异常
 */
public class RemoteServiceException extends RuntimeException {

	public RemoteServiceException(String message) {
		super(message);
	}

	public RemoteServiceException() {}
}
