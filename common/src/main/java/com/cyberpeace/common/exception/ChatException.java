package com.cyberpeace.common.exception;

import com.cyberpeace.constant.BaseI18nErrorMsg;

public class ChatException extends BusinessException {

	public ChatException(BaseI18nErrorMsg i18nErrorMsg) {
		super(i18nErrorMsg);
	}
}
