package com.cyberpeace.common.util;

import com.cyberpeace.common.baseinterface.GenericEnum;

import java.util.Arrays;

public class EnumUtils {

	/***
	 * 根据code获取枚举
	 */
	public static <T extends GenericEnum> T getEnumByCode(Integer code, Class<T> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants()).filter(t -> t.getCode().equals(code))
				.findFirst().orElse(null);
	}

	/***
	 * 根据code获取描述
	 */
	public static <T extends GenericEnum> String getEnumDescByCode(Integer code,
			Class<T> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants()).filter(t -> t.getCode().equals(code))
				.map(T::getDesc).findFirst().orElse(null);
	}

}
