package com.cyberpeace.common.exception;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.constant.BaseI18nErrorMsg;

/**
 * @author yinqingrong
 * 调用mp的服务出错，抛出此异常
 */
public class RemoteServiceException extends I18nException {

	public RemoteServiceException() {
		super(ErrorCode.REMOTE_SERVICE_EXCEPTION);
	}

	public RemoteServiceException(BaseI18nErrorMsg i18nErrorMsg) {
		super(i18nErrorMsg);
	}

}
