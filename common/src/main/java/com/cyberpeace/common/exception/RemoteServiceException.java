package com.cyberpeace.common.exception;

/**
 * 远程Service例外
 *
 * @author yinqingrong
 * 调用mp的服务出错，抛出此异常@date 2024/11/08@see RuntimeException
 * @version 1.0.0
 * @date 2024/12/04
 * @see RuntimeException
 * @since 1.0.0
 */
public class RemoteServiceException extends RuntimeException {

	public RemoteServiceException(String message) {
		super(message);
	}

	public RemoteServiceException() {}
}
