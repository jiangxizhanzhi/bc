package com.cyberpeace.common.exception;

import com.cyberpeace.constant.BaseI18nErrorMsg;
import lombok.Getter;

/**
 * i18n 异常
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @see RuntimeException
 * @since 1.0.0
 */
public class I18nException extends RuntimeException {
	/**
	 * i18n 错误 msg
	 *
	 * @see BaseI18nErrorMsg
	 */
	@Getter
	private final BaseI18nErrorMsg i18nErrorMsg;

	public I18nException(BaseI18nErrorMsg i18nErrorMsg) {
		this.i18nErrorMsg = i18nErrorMsg;
	}
}
