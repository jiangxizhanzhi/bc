package com.cyberpeace.common.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.cyberpeace.common.baseinterface.GenericEnum;

/**
 * enum utils
 *
 * @author SN
 * @date 2024/11/07
 */
public class EnumUtils {

	/**
	 * *
	 * 根据code获取枚举
	 *
	 * @param code      代号
	 * @param enumClass enum 类
	 * @return {@code T }
	 */
	public static <T extends GenericEnum> T getEnumByCode(Integer code, Class<T> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants()).filter(t -> t.getCode().equals(code))
				.findFirst().orElse(null);
	}

	/**
	 * *
	 * 根据code获取描述
	 *
	 * @param code      代号
	 * @param enumClass enum 类
	 * @return {@code String }
	 */
	public static <T extends GenericEnum> String getEnumDescByCode(Integer code,
			Class<T> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants()).filter(t -> t.getCode().equals(code))
				.map(T::getDesc).findFirst().orElse(null);
	}

	/**
	 * *
	 * 根据desc获取code
	 *
	 * @param desc      描述
	 * @param enumClass enum 类
	 * @return {@code Integer }
	 */
	public static <T extends GenericEnum> Integer getEnumCodeByDesc(String desc,
			Class<T> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants()).filter(t -> t.getDesc().equals(desc))
				.map(T::getCode).findFirst().orElse(null);
	}

	/**
	 * 获取枚举值列表
	 *
	 * @param enumClass
	 * @return
	 */
	public static <T extends GenericEnum> List<Integer> getListCode(Class<T> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants()).map(t -> t.getCode())
				.collect(Collectors.toList());
	}

	/**
	 * 获取列表描述
	 *
	 * @param enumClass enum 类
	 * @return {@code List<String> }
	 */
	public static <T extends GenericEnum> List<String> getListDesc(Class<T> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants()).map(GenericEnum::getDesc).collect(Collectors.toList());
	}

}
