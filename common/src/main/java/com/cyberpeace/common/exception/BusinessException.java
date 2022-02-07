package com.cyberpeace.common.exception;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.constant.BaseI18nErrorMsg;

/**
 * 业务处理异常
 *
 * @author yinqingrong
 */
public class BusinessException extends I18nException {

	public BusinessException(BaseI18nErrorMsg i18nErrorMsg) {
		super(i18nErrorMsg);
	}

	public BusinessException() {
		super(ErrorCode.BUSINESS_ERROR);
	}
}
