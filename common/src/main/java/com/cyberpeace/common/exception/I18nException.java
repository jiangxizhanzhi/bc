package com.cyberpeace.common.exception;

import com.cyberpeace.constant.BaseI18nErrorMsg;
import lombok.Getter;

/**
 * i18n 异常
 *
 * @author SN
 * @date 2024/11/07
 */
public class I18nException extends RuntimeException {
	/**
	 * i18n 错误 msg
	 */
	@Getter
	private final BaseI18nErrorMsg i18nErrorMsg;

	/**
	 * i18n 异常
	 *
	 * @param i18nErrorMsg i18n 错误 msg
	 */
	public I18nException(BaseI18nErrorMsg i18nErrorMsg) {
		this.i18nErrorMsg = i18nErrorMsg;
	}
}
