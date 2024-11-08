package com.cyberpeace.common.exception;

/**
 * 远程Service例外
 *
 * @author yinqingrong
 * 调用mp的服务出错，抛出此异常@date 2024/11/08
 */
public class RemoteServiceException extends RuntimeException {

	public RemoteServiceException(String message) {
		super(message);
	}

	public RemoteServiceException() {}
}
