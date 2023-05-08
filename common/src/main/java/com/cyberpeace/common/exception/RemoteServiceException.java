package com.cyberpeace.common.exception;

/**
 * 远程服务异常
 *
 * @author yinqingrong
 * 调用mp的服务出错，抛出此异常
 * @date 2023/05/06
 */
public class RemoteServiceException extends RuntimeException {

	/**
	 * 远程服务异常
	 *
	 * @param message 消息
	 */
	public RemoteServiceException(String message) {
		super(message);
	}

	/**
	 * 远程服务异常
	 */
	public RemoteServiceException() {}
}
