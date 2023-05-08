package com.cyberpeace.common.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.cyberpeace.common.baseinterface.GenericEnum;

/**
 * 枚举工具
 *
 * @author cyberpeace
 * @date 2023/05/06
 */
public class EnumUtils {

	/**
	 * 得到枚举代码*
	 * 根据code获取枚举
	 *
	 * @param code      代码
	 * @param enumClass 枚举类
	 * @return {@link T}
	 */
	public static <T extends GenericEnum> T getEnumByCode(Integer code, Class<T> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants()).filter(t -> t.getCode().equals(code))
				.findFirst().orElse(null);
	}

	/**
	 * 根据code获取描述
	 * 得到enum desc代码*
	 *
	 * @param code      代码
	 * @param enumClass 枚举类
	 * @return {@link String}
	 */
	public static <T extends GenericEnum> String getEnumDescByCode(Integer code,
			Class<T> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants()).filter(t -> t.getCode().equals(code))
				.map(T::getDesc).findFirst().orElse(null);
	}

	/**
	、	 * 根据desc获取code
	 *
	 * @param desc      desc
	 * @param enumClass 枚举类
	 * @return {@link Integer}
	 */
	public static <T extends GenericEnum> Integer getEnumCodeByDesc(String desc,
			Class<T> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants()).filter(t -> t.getDesc().equals(desc))
				.map(T::getCode).findFirst().orElse(null);
	}

	/**
	、	 * 获取枚举值列表
	 *
	 * @param enumClass 枚举类
	 * @return {@link List}<{@link Integer}>
	 */
	public static <T extends GenericEnum> List<Integer> getListCode(Class<T> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants()).map(t -> t.getCode())
				.collect(Collectors.toList());
	}

}
