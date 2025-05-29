package com.cyberpeace.common.util;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.cyberpeace.common.baseinterface.GenericEnum;

/**
 * 这段代码是一个工具类 `EnumUtils`，主要用于操作实现了 `GenericEnum` 接口的枚举类型。以下是代码的主要功能和结构分析： 
 * 1. **泛型支持**： 
 * - 该类使用了泛型 `<T extends GenericEnum>`，确保方法可以处理任何实现了 `GenericEnum` 接口的枚举类。 
 * 2. **主要方法**： 
 * - `getEnumByCode`：根据传入的 `code` 值，从枚举类中找到对应的枚举对象。 
 * - `getEnumDescByCode`：根据 `code` 值获取对应的枚举描述（`desc`）。 
 * - `getEnumCodeByDesc`：根据 `desc` 值获取对应的枚举代码（`code`）。 
 * - `getListCode`：获取枚举类中所有 `code` 的列表。 
 * - `getListDesc`：获取枚举类中所有 `desc` 的列表。 
 * 3. **核心逻辑**： 
 * - 所有方法都基于 `enumClass.getEnumConstants()` 获取枚举常量数组，并通过 Java 8 的流式操作（`Arrays.stream` 和 `filter/map`）进行筛选和映射。 
 * - 每个方法都返回一个默认值 `null`，当没有匹配结果时不会抛出异常。 
 * 4. **适用场景**： 
 * - 这些方法适用于需要频繁根据 `code` 或 `desc` 查询枚举值的场景，提高了代码的可读性和复用性。 
 * 总结：`EnumUtils` 提供了一系列实用的方法来操作实现了 `GenericEnum` 接口的枚举类，简化了常见的枚举查询和集合操作。 
**/
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

