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
 * @date 2024/11/18
 * @since 1.0.0
 */
public class StreamApiUtils {

	/**
	 * 获取集合中的某列 如果集合为空返
	 *
	 * @param collection
	 * @param mapperValue
	 * @return
	 * @throws
	 * @see Collection<E>
	 * @see Function<E, V>
	 * @see List<V>
	 */
	public static <E, V> List<V> getColumn(Collection<E> collection, Function<E, V> mapperValue) {
		if (null == collection) {
			return Lists.newArrayList();
		}
		return collection.stream().map(mapperValue).collect(Collectors.toList());
	}

	/**
	 * 将集合转为map
	 *
	 * @param collection
	 * @param keyMapper
	 * @param valueMapper
	 * @return
	 * @throws
	 * @see Collection<E>
	 * @see Function<E, K>
	 * @see Function<E, V>
	 * @see Map<K, V>
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
	 *
	 * @param collection
	 * @param mapperValue
	 * @return
	 * @throws
	 * @see Collection<E>
	 * @see Function<E, K>
	 * @see Map<K, List<E>>
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
	 *
	 * @param collection
	 * @param predicate
	 * @return
	 * @throws
	 * @see Collection<E>
	 * @see Predicate<? super E>
	 * @see List<E>
	 */
	public static <E> List<E> filter(Collection<E> collection, Predicate<? super E> predicate) {
		return collection.stream().filter(predicate).collect(Collectors.toList());
	}

	/**
	 * 简单map操作
	 *
	 * @param collection
	 * @param mapperValue
	 * @return
	 * @throws
	 * @see Collection<E>
	 * @see Function<E, T>
	 * @see List<T>
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
	 *
	 * @param collection
	 * @param mapperValue
	 * @return
	 * @throws
	 * @see Collection<E>
	 * @see Function<E, List<T>>
	 * @see List<T>
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
	 *
	 * @param collection
	 * @param mapper
	 * @return
	 * @throws
	 * @see Collection<E>
	 * @see Function<E, List<T>>
	 * @see List<T>
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
	 *
	 * @param collection
	 * @param mapper
	 * @return
	 * @throws
	 * @see Collection<E>
	 * @see ToDoubleFunction<? super E>
	 * @see Double
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
	 *
	 * @param collection
	 * @return
	 * @throws
	 * @see Collection<E>
	 * @see List<E>
	 */
	public static <E> List<E> distinct(Collection<E> collection) {
		return collection.stream().distinct().collect(Collectors.toList());
	}

	/**
	 * 根据某个字段去重
	 *
	 * @param collection
	 * @param mapper
	 * @return
	 * @throws
	 * @see Collection<E>
	 * @see Function<? super E, Object>
	 * @see List<E>
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
	 *
	 * @param collection
	 * @param keyExtractor
	 * @return
	 * @throws
	 * @see Collection<E>
	 * @see Function<? super E, ? extends U>
	 * @see List<E>
	 */
	public static <E, U extends Comparable<? super U>> List<E>
			sortByColumn(Collection<E> collection, Function<? super E, ? extends U> keyExtractor) {
		return collection.stream().sorted(Comparator.comparing(keyExtractor))
				.collect(Collectors.toList());
	}

	/**
	 * 获取分页数据
	 *
	 * @param data
	 * @param currentPage
	 * @param pageSize
	 * @return
	 * @throws
	 * @see List<T>
	 * @see long
	 * @see long
	 * @see List<T>
	 */
	public static <T> List<T> pageByList(List<T> data, long currentPage, long pageSize) {

		if (currentPage <= CommonConstant.ZERO) {
			currentPage = CommonConstant.ONE;
		}

		return data.stream().skip((currentPage - CommonConstant.ONE) * pageSize).limit(pageSize)
				.collect(Collectors.toList());
	}


	/**
	 * 自映射
	 *
	 * @param collection    收集
	 * @param keyMapper     钥匙Mapper
	 * @param valueMapper   值Mapper
	 * @param mergeFunction merge 函数
	 * @return {@code Map<K, V> }
	 * @throws
	 * @see Collection<E>
	 * @see Function<E, K>
	 * @see Function<E, V>
	 * @see BinaryOperator<V>
	 * @see Map<K, V>
	 */
	public static <K, V, E> Map<K, V> toMap(Collection<E> collection, Function<E, K> keyMapper,
											Function<E, V> valueMapper, BinaryOperator<V> mergeFunction) {
		if (CollectionUtils.isEmpty(collection)) {
			return Maps.newHashMap();
		}
		return collection.stream().collect(Collectors.toMap(keyMapper, valueMapper,mergeFunction));
	}
}
