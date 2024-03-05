package com.cyberpeace.common.mdparser.convert;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MdConvert {

	/***
	 * 根据code获取枚举
	 */
	public static <T extends MdConvertEnum, K> T convertEnum(K k, Class<T> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants()).filter(t -> t.to().equals(k)).findFirst()
				.orElse(null);
	}

	/***
	 * 根据to获取reTo
	 */
	public static <T extends MdConvertEnum<K, V>, K, V> V convertValue(K k, Class<T> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants()).filter(t -> t.to().equals(k))
				.map(T::reTo).findFirst().orElse(null);
	}

	/***
	 * 根据reTo获取to
	 */
	public static <T extends MdConvertEnum<K, V>, K, V> K convertKey(V v, Class<T> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants()).filter(t -> t.reTo().equals(v))
				.map(T::to).findFirst().orElse(null);
	}

	/**
	 * 获取枚举值列表
	 * @param enumClass
	 * @param <T>
	 * @return
	 */
	public static <T extends MdConvertEnum<K, K>, K> List<K> getListCode(Class<T> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants()).map(t -> t.to())
				.collect(Collectors.toList());
	}

	private MdConvert() {}

}
