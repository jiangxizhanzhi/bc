package com.cyberpeace.common.mdparser.convert;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 中路闪兑
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/08
 * @since 1.0.0
 */
/**
 * 这段代码定义了一个名为 `MdConvert` 的工具类，用于根据枚举类及其相关方法进行转换操作。以下是代码的主要功能和结构分析： 
 * 1. **类和泛型设计**： 
 * - 类 `MdConvert` 是一个静态工具类，提供了多种基于枚举的操作。 
 * - 使用了泛型 `<T extends MdConvertEnum<K, V>, K, V>` 来支持不同类型的枚举及其关联值。 
 * 2. **核心方法**： 
 * - `convertEnum(K k, Class<T> enumClass)`：根据指定的 `key`（`k`）从枚举类中查找对应的枚举实例。 
 * - `convertValue(K k, Class<T> enumClass)`：根据 `key` 查找对应的 `value`。 
 * - `convertKey(V v, Class<T> enumClass)`：根据 `value` 查找对应的 `key`。 
 * - `getListCode(Class<T> enumClass)`：获取枚举类中所有 `key` 值的列表。 
 * 3. **依赖的接口 `MdConvertEnum`**： 
 * - 方法 `to()` 和 `reTo()` 是枚举实现中必须定义的，分别用于获取 `key` 和 `value`。 
 * 4. **流式操作**： 
 * - 使用 Java 8 的 Stream API 对枚举常量进行过滤、映射和查找操作。 
 * 5. **用途**： 
 * - 该工具类适用于需要频繁进行枚举值与键值对之间转换的场景，例如状态码与描述之间的映射。 
 * 总结：这是一个灵活且通用的枚举转换工具类，通过泛型和流式操作实现了高效的枚举值管理。 
**/
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

