package com.cyberpeace.common.mdparser.convert;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 中路闪兑
 *
 * @author SN
 * @date 2024/11/08
 */
public class MdConvert {

	/**
	 * *
	 * 根据code获取枚举
	 *
	 * @param k         k
	 * @param enumClass enum 类
	 * @return {@code T }
	 */
	public static <T extends MdConvertEnum, K> T convertEnum(K k, Class<T> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants()).filter(t -> t.to().equals(k)).findFirst()
				.orElse(null);
	}

	/**
	 * *
	 * 根据to获取reTo
	 *
	 * @param k         k
	 * @param enumClass enum 类
	 * @return {@code V }
	 */
	public static <T extends MdConvertEnum<K, V>, K, V> V convertValue(K k, Class<T> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants()).filter(t -> t.to().equals(k))
				.map(T::reTo).findFirst().orElse(null);
	}

	/**
	 * *
	 * 根据reTo获取to
	 *
	 * @param v         v
	 * @param enumClass enum 类
	 * @return {@code K }
	 */
	public static <T extends MdConvertEnum<K, V>, K, V> K convertKey(V v, Class<T> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants()).filter(t -> t.reTo().equals(v))
				.map(T::to).findFirst().orElse(null);
	}

	/**
	 * 获取枚举值列表
	 *
	 * @param enumClass
	 * @return
	 */
	public static <T extends MdConvertEnum<K, K>, K> List<K> getListCode(Class<T> enumClass) {
		return Arrays.stream(enumClass.getEnumConstants()).map(t -> t.to())
				.collect(Collectors.toList());
	}

}
