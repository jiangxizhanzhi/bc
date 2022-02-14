package com.cyberpeace.common.exception;

import com.cyberpeace.common.constant.ErrorCode;

/**
 * @author yinqingrong
 * 调用mp的服务出错，抛出此异常
 */
public class RemoteServiceException extends I18nException {

	public RemoteServiceException() {
		this("");
	}

	public RemoteServiceException(String message) {
		super(ErrorCode.REMOTE_SERVICE_EXCEPTION.parameterize(message));
	}

}
