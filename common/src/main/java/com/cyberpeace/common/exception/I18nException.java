package com.cyberpeace.common.exception;

import com.cyberpeace.constant.BaseI18nErrorMsg;
import lombok.Getter;

public class I18nException extends RuntimeException {
	@Getter
	private final BaseI18nErrorMsg i18nErrorMsg;

	public I18nException(BaseI18nErrorMsg i18nErrorMsg) {
		this.i18nErrorMsg = i18nErrorMsg;
	}
}
