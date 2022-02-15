package com.cyberpeace.common.exception;

import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.common.util.ConfigUtil;

import lombok.Getter;

/**
 * 业务处理异常
 *
 * @author yinqingrong
 */
public class BusinessException extends RuntimeException {

	@Getter
	private String errorMessage;

	public BusinessException(String code) {
		super(code);
		errorMessage = getMessageByLanguage(code);
	}

	public BusinessException(String code, Object... args) {
		super(code);
		errorMessage = String.format(getMessageByLanguage(code), args);
	}

	public static String getMessageByLanguage(String code) {
		if (CommonContext.getLanguage().equals(WebConstant.LANGUAGE_EN)) {
			return ConfigUtil.getContextProperty(code + ".en");
		} else {
			return ConfigUtil.getContextProperty(code + ".cn");
		}
	}

	public BusinessException() {
		super();
	}
}
