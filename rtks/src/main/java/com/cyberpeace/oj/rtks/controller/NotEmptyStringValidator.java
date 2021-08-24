package com.cyberpeace.oj.rtks.controller;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class NotEmptyStringValidator implements ConstraintValidator<NotEmptyString, String> {

	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		// 允许为null
		if (value == null) {
			return true;
		}

		return !value.isEmpty();
	}
}
