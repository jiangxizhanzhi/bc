package com.cyberpeace.common.exception;

import com.cyberpeace.constant.BaseI18nErrorMsg;

import lombok.Getter;

/**
 * i18n异常
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public class I18nException extends RuntimeException {

	/**
	 * i18n错误信息
	 */
	@Getter
	private final BaseI18nErrorMsg i18nErrorMsg;

	/**
	 * i18n异常
	 *
	 * @param i18nErrorMsg i18n错误信息
	 */
	public I18nException(BaseI18nErrorMsg i18nErrorMsg) {
		this.i18nErrorMsg = i18nErrorMsg;
	}
}
