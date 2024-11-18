package com.cyberpeace.common.exception;

/**
 * 远程Service例外
 *
 * @author yinqingrong
 * 调用mp的服务出错，抛出此异常
 * @version 1.0.0
 * @date 2024/11/07
 * @see RuntimeException
 * @since 1.0.0
 */
public class RemoteServiceException extends RuntimeException {

	/**
	 * 远程Service例外
	 *
	 * @param message 消息
	 */
	public RemoteServiceException(String message) {
		super(message);
	}

	/**
	 * 远程Service例外
	 */
	public RemoteServiceException() {}
}
