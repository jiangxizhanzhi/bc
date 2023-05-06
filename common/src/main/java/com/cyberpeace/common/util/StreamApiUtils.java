package com.cyberpeace.common.util;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;

import com.cyberpeace.common.constant.CommonConstant;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 流api工具
 *
 * @author cyberpeace
 * @date 2023/05/06
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
		List<V> list = collection.stream().map(mapperValue).collect(Collectors.toList());
		return list;
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

}
