package com.cyberpeace.common.model;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.cyberpeace.common.baseinterface.EnumValid;

/**
 * enum验证器
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public class EnumValidator implements ConstraintValidator<EnumValid, Integer> {

	// 枚举校验注解
	private EnumValid annotation;

	/**
	 * 初始化
	 *
	 * @param constraintAnnotation 约束注释
	 */
	@Override
	public void initialize(EnumValid constraintAnnotation) {

		annotation = constraintAnnotation;
	}

	/**
	 * 有效
	 *
	 * @param value                      价值
	 * @param constraintValidatorContext 约束验证器上下文
	 * @return boolean
	 */
	@Override
	public boolean isValid(Integer value, ConstraintValidatorContext constraintValidatorContext) {
		boolean result = false;

		Class<?> cls = annotation.target();
		boolean ignoreEmpty = annotation.ignoreEmpty();

		// target为枚举，并且value有值，或者不忽视空值，才进行校验
		if (cls.isEnum() && (value != null || !ignoreEmpty)) {

			Object[] objects = cls.getEnumConstants();
			for (Object obj : objects) {
				// 使用此注解的枚举类需要重写toString方法，改为需要验证的值
				if (obj.toString().equals(String.valueOf(value))) {
					result = true;
					break;
				}
			}
		} else {
			result = true;
		}
		return result;
	}
}