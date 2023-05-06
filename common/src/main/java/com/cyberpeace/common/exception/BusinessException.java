package com.cyberpeace.common.exception;

import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.common.util.ConfigUtil;

import lombok.Getter;

/**
 * 业务异常
 * 业务处理异常
 *
 * @author yinqingrong
 * @date 2023/05/06
 */
public class BusinessException extends RuntimeException {

	@Getter
	private String errorMessage;

	/**
	 * 业务异常
	 *
	 * @param code 代码
	 */
	public BusinessException(String code) {
		super(code);
		errorMessage = getMessageByLanguage(code);
	}

	/**
	 * 业务异常
	 *
	 * @param code 代码
	 * @param args arg游戏
	 */
	public BusinessException(String code, Object... args) {
		super(code);
		errorMessage = String.format(getMessageByLanguage(code), args);
	}

	/**
	 * 业务异常
	 */
	public BusinessException() {
		super();
	}

	/**
	 * 得到消息语言
	 *
	 * @param code 代码
	 * @return {@link String}
	 */
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
