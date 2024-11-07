package com.cyberpeace.common.exception;

import com.cyberpeace.common.constant.WebConstant;
import com.cyberpeace.common.model.CommonContext;
import com.cyberpeace.common.util.ConfigUtil;

import lombok.Getter;

/**
 * 业务处理异常
 *
 * @author yinqingrong
 * @date 2024/11/07
 */
public class BusinessException extends RuntimeException {

	/**
	 * 错误信息
	 */
	@Getter
	private String errorMessage;

	/**
	 * 业务例外
	 *
	 * @param code 代号
	 */
	public BusinessException(String code) {
		super(code);
		errorMessage = getMessageByLanguage(code);
	}

	/**
	 * 业务例外
	 *
	 * @param code 代号
	 * @param args 参数
	 */
	public BusinessException(String code, Object... args) {
		super(code);
		errorMessage = String.format(getMessageByLanguage(code), args);
	}

	/**
	 * 业务例外
	 */
	public BusinessException() {
		super();
	}

	/**
	 * 获取消息由语言
	 *
	 * @param code 代号
	 * @return {@code String }
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
