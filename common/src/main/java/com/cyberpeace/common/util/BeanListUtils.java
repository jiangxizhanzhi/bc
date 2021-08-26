package com.cyberpeace.common.util;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

import org.springframework.beans.BeanUtils;

/**
 * json序列化和反序列化工具类
 */
public class BeanListUtils extends BeanUtils {

	public static <S, T> List<T> copyListProperties(List<S> sources, Supplier<T> target) {
		List<T> list = new ArrayList<>(sources.size());
		for (S source : sources) {
			T t = target.get();
			copyProperties(source, t);
			list.add(t);
		}
		return list;
	}

}