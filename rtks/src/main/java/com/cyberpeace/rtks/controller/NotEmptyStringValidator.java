package com.cyberpeace.rtks.controller;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * not 空字符串验证器
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/19
 * @see ConstraintValidator
 * @since 1.0.0
 */
public class NotEmptyStringValidator implements ConstraintValidator<NotEmptyString, String> {

	/**
	 * 是否有效
	 *
	 * @param value   值
	 * @param context 上下文
	 * @return boolean
	 * @throws
	 * @see String
	 * @see ConstraintValidatorContext
	 * @see boolean
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
