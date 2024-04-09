package com.cyberpeace.common.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.cyberpeace.common.baseinterface.GenericEnum;

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

	/***
	 * 根据desc获取code
	 */
	public static <T extends GenericEnum> Integer getEnumCodeByDesc(String desc,
			Class<T> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants()).filter(t -> t.getDesc().equals(desc))
				.map(T::getCode).findFirst().orElse(null);
	}

	/**
	 * 获取枚举值列表
	 * @param enumClass
	 * @param <T>
	 * @return
	 */
	public static <T extends GenericEnum> List<Integer> getListCode(Class<T> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants()).map(t -> t.getCode())
				.collect(Collectors.toList());
	}

	public static <T extends GenericEnum> List<String> getListDesc(Class<T> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants()).map(GenericEnum::getDesc).collect(Collectors.toList());
	}

}
