package com.cyberpeace.common.util;

import com.cyberpeace.common.constant.CommonConstant;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.collections.CollectionUtils;
import org.checkerframework.checker.units.qual.K;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
		return collection.stream().map(mapperValue)
				.filter(e -> null != e).collect(Collectors.toList());
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

}
