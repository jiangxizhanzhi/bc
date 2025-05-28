/**
 * ### 代码分析 
 * #### 1. **类概述** 
 * - 类名为 `StreamApiUtils`，位于包 `com.cyberpeace.common.util` 下。 
 * - 提供了一系列基于 Java Stream API 的工具方法，用于简化集合操作。 
 * #### 2. **主要功能模块** 
 * 以下是对类中方法的分类和功能总结： 
 * ##### 2.1. **集合转换** 
 * - **`getColumn`**: 
 * 功能：从集合中提取指定列（通过 `Function` 映射）。 
 * 参数： 
 * - `collection`: 输入集合。 
 * - `mapperValue`: 提取列的映射函数。 
 * 返回值：提取后的列表。 
 * - **`toMap`**: 
 * 功能：将集合转换为 `Map`。 
 * 参数： 
 * - `keyMapper`: 定义键的映射函数。 
 * - `valueMapper`: 定义值的映射函数。 
 * 返回值：生成的 `Map`。 
 * 可选参数：支持合并冲突的 `BinaryOperator`。 
 * - **`groupBy`**: 
 * 功能：按指定字段对集合进行分组。 
 * 参数： 
 * - `mapperValue`: 分组依据的映射函数。 
 * 返回值：分组后的 `Map`。 
 * ##### 2.2. **集合过滤与映射** 
 * - **`filter`**: 
 * 功能：根据条件过滤集合元素。 
 * 参数： 
 * - `predicate`: 过滤条件。 
 * 返回值：过滤后的列表。 
 * - **`map`**: 
 * 功能：对集合元素进行映射操作。 
 * 参数： 
 * - `mapperValue`: 映射函数。 
 * 返回值：映射后的列表。 
 * - **`flatMap`**: 
 * 功能：将对象中的集合字段展平为单一列表。 
 * 参数： 
 * - `mapperValue`: 将对象映射为集合的函数。 
 * 返回值：展平后的列表。 
 * - **`distinctFlatMap`**: 
 * 功能：去重后将对象中的集合字段展平为单一列表。 
 * 参数： 
 * - `mapperValue`: 将对象映射为集合的函数。 
 * 返回值：去重后的展平列表。 
 * ##### 2.3. **统计与计算** 
 * - **`mapToDouble`**: 
 * 功能：计算集合中某列的 double 值总和。 
 * 参数： 
 * - `mapper`: 将对象映射为 double 的函数。 
 * 返回值：计算结果。 
 * ##### 2.4. **去重与排序** 
 * - **`distinct`**: 
 * 功能：去除集合中的重复元素。 
 * 返回值：去重后的列表。 
 * - **`distinctByColumn`**: 
 * 功能：根据指定字段去重。 
 * 参数： 
 * - `mapper`: 定义去重依据的映射函数。 
 * 返回值：去重后的列表。 
 * - **`sortByColumn`**: 
 * 功能：根据指定字段对集合排序。 
 * 参数： 
 * - `keyExtractor`: 排序依据的映射函数。 
 * 返回值：排序后的列表。 
 * ##### 2.5. **分页** 
 * - **`pageByList`**: 
 * 功能：实现分页功能。 
 * 参数： 
 * - `data`: 输入数据列表。 
 * - `currentPage`: 当前页码。 
 * - `pageSize`: 每页大小。 
 * 返回值：当前页的数据列表。 
 * #### 3. **工具库依赖** 
 * - 使用了以下外部工具库： 
 * - Apache Commons Collections (`org.apache.commons.collections.CollectionUtils`) 
 * - Guava (`com.google.common.collect.Lists`, `com.google.common.collect.Maps`) 
 * - 自定义常量类 (`com.cyberpeace.common.constant.CommonConstant`) 
 * #### 4. **设计特点** 
 * - **泛型支持**：所有方法均使用泛型，支持多种类型的操作。 
 * - **空集合处理**：对于空集合，返回默认值（如空列表或空映射），避免运行时异常。 
 * - **流式操作**：充分利用 Java 8+ 的 Stream API，简洁且高效。 
 * - **灵活性**：提供多种组合方式（如去重、分组、排序等）。 
 * #### 5. **适用场景** 
 * - 适用于需要频繁操作集合的场景，例如数据处理、报表生成、业务逻辑封装等。 
 * - 特别适合需要对集合进行复杂操作（如分组、去重、排序等）的情况。 
 * #### 6. **潜在改进点** 
 * - **性能优化**：某些方法可能涉及多次遍历集合（如 `distinctByColumn`），可以进一步优化。 
 * - **文档完善**：部分方法的注释可以更详细地描述参数含义和返回值的具体行为。 
 * - **并发支持**：若需在高并发场景下使用，可考虑引入线程安全的集合操作。 
 * 总结来说，`StreamApiUtils` 是一个功能强大且灵活的工具类，能够显著提高集合操作的开发效率。 
**/
package com.cyberpeace.common.util;

import java.util.*;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

import com.cyberpeace.common.constant.CommonConstant;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 流Apiutils
 *
 * @author SN
 * @version 1.0.0
 * @date 2025/05/28
 * @since 1.0.0
 */
public class StreamApiUtils {

	/**
	 * 获取集合中的某列 如果集合为空返
	 * @param collection
	 * @param mapperValue
	 * @param <E>
	 * @param <V>
	 * @return
	 */
	public static <E, V> List<V> getColumn(Collection<E> collection, Function<E, V> mapperValue) {
		if (null == collection) {
			return Lists.newArrayList();
		}
		return collection.stream().map(mapperValue).collect(Collectors.toList());
	}

	/**
	 * 将集合转为map
	 * @param collection
	 * @param keyMapper
	 * @param valueMapper
	 * @param <K>
	 * @param <V>
	 * @param <E>
	 * @return
	 */
	public static <K, V, E> Map<K, V> toMap(Collection<E> collection, Function<E, K> keyMapper,
			Function<E, V> valueMapper) {
		if (CollectionUtils.isEmpty(collection)) {
			return Maps.newHashMap();
		}
		return collection.stream().collect(Collectors.toMap(keyMapper, valueMapper));
	}

	/**
	 * 集合分组
	 * @param collection
	 * @param mapperValue
	 * @param <K>
	 * @param <E>
	 * @return
	 */
	public static <K, E> Map<K, List<E>> groupBy(Collection<E> collection,
			Function<E, K> mapperValue) {
		if (CollectionUtils.isEmpty(collection)) {
			return Maps.newHashMap();
		}
		return collection.stream().collect(Collectors.groupingBy(mapperValue));
	}

	/**
	 * 简单过滤，仅支持一个筛选条件
	 * @param collection
	 * @param predicate
	 * @param <E>
	 * @return
	 */
	public static <E> List<E> filter(Collection<E> collection, Predicate<? super E> predicate) {
		return collection.stream().filter(predicate).collect(Collectors.toList());
	}

	/**
	 * 简单map操作
	 * @param collection
	 * @param mapperValue
	 * @param <E>
	 * @param <T>
	 * @return
	 */
	public static <E, T> List<T> map(Collection<E> collection, Function<E, T> mapperValue) {
		if (CollectionUtils.isEmpty(collection)) {
			return Lists.newArrayList();
		}
		return collection.stream().map(mapperValue).filter(e -> null != e)
				.collect(Collectors.toList());
	}

	/**
	 * 将对象中的某个为列表的集合收集为一个集合
	 * @param collection
	 * @param mapperValue
	 * @param <E>
	 * @param <T>
	 * @return
	 */
	public static <E, T> List<T> flatMap(Collection<E> collection,
			Function<E, List<T>> mapperValue) {
		if (CollectionUtils.isEmpty(collection)) {
			return Lists.newArrayList();
		}

		return collection.stream().map(mapperValue).flatMap(Collection::stream)
				.collect(Collectors.toList());
	}

	/**
	 * 去重收集
	 * @param collection
	 * @param mapper
	 * @param <E>
	 * @param <T>
	 * @return
	 */
	public static <E, T> List<T> distinctFlatMap(Collection<E> collection,
			Function<E, List<T>> mapper) {
		if (CollectionUtils.isEmpty(collection)) {
			return Lists.newArrayList();
		}

		return collection.stream().map(mapper).flatMap(Collection::stream).distinct()
				.collect(Collectors.toList());
	}

	/**
	 * 计算结合某列的double值
	 * @param collection
	 * @param mapper
	 * @param <E>
	 * @return
	 */
	public static <E> Double mapToDouble(Collection<E> collection,
			ToDoubleFunction<? super E> mapper) {

		if (CollectionUtils.isEmpty(collection)) {
			return CommonConstant.ZERO.doubleValue();
		}

		return collection.stream().mapToDouble(mapper).sum();
	}

	/**
	 * 去重
	 * @param collection
	 * @param <E>
	 * @return
	 */
	public static <E> List<E> distinct(Collection<E> collection) {
		return collection.stream().distinct().collect(Collectors.toList());
	}

	/**
	 * 根据某个字段去重
	 * @param collection
	 * @param mapper
	 * @param <E>
	 * @return
	 */
	public static <E> List<E> distinctByColumn(Collection<E> collection,
			Function<? super E, Object> mapper) {
		Map<Object, Boolean> hashMap = new HashMap<>();
		return collection.stream()
				.filter(t -> hashMap.putIfAbsent(mapper.apply(t), CommonConstant.TRUE) == null)
				.collect(Collectors.toList());
	}

	/**
	 * 根据某个字段进行排序
	 * @param collection
	 * @param keyExtractor
	 * @param <E>
	 * @param <U>
	 * @return
	 */
	public static <E, U extends Comparable<? super U>> List<E>
			sortByColumn(Collection<E> collection, Function<? super E, ? extends U> keyExtractor) {
		return collection.stream().sorted(Comparator.comparing(keyExtractor))
				.collect(Collectors.toList());
	}

	/**
	 * 获取分页数据
	 * @param data
	 * @param currentPage
	 * @param pageSize
	 * @param <T>
	 * @return
	 */
	public static <T> List<T> pageByList(List<T> data, long currentPage, long pageSize) {

		if (currentPage <= CommonConstant.ZERO) {
			currentPage = CommonConstant.ONE;
		}

		return data.stream().skip((currentPage - CommonConstant.ONE) * pageSize).limit(pageSize)
				.collect(Collectors.toList());
	}


	public static <K, V, E> Map<K, V> toMap(Collection<E> collection, Function<E, K> keyMapper,
											Function<E, V> valueMapper, BinaryOperator<V> mergeFunction) {
		if (CollectionUtils.isEmpty(collection)) {
			return Maps.newHashMap();
		}
		return collection.stream().collect(Collectors.toMap(keyMapper, valueMapper,mergeFunction));
	}
}
