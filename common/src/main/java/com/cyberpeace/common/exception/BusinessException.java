package com.cyberpeace.common.exception;

import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.common.util.ConfigUtil;

import lombok.Getter;

/**
 * 业务处理异常
 *
 * @author yinqingrong@date 2024/11/08
 */
public class BusinessException extends RuntimeException {

	/**
	 * 错误信息
	 */
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

	public BusinessException() {
		super();
	}

	public static String getMessageByLanguage(String code) {
		String ret;
		if (CommonContext.getLanguage().equals(WebConstant.LANGUAGE_EN)) {
			ret = ConfigUtil.getContextProperty(code + ".en");
		} else {
			ret = ConfigUtil.getContextProperty(code + ".cn");
		}
		return ret == null ? "" : ret;
	}
}
