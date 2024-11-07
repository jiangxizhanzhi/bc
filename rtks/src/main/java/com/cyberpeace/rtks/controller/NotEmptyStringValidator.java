package com.cyberpeace.rtks.controller;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * not 空字符串验证器
 *
 * @author SN
 * @date 2024/11/07
 */
public class NotEmptyStringValidator implements ConstraintValidator<NotEmptyString, String> {

	/**
	 * 有效
	 *
	 * @param value   值
	 * @param context 上下文
	 * @return boolean
	 */
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		// 允许为null
		if (value == null) {
			return true;
		}

		return !value.isEmpty();
	}
}
