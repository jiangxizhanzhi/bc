package com.cyberpeace.common.util;

import java.util.List;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * json序列化和反序列化工具类
 */
public class BeanUtils {

	private BeanUtils() {}

	/**
	 * 浅kaobei
	 * @param source
	 * @param target
	 * @param <S>
	 * @param <T>
	 * @return
	 */
	public static <S, T> T copy(S source, Class<T> target) {
		T result = org.springframework.beans.BeanUtils.instantiateClass(target);

		org.springframework.beans.BeanUtils.copyProperties(source, result);

		return result;
	}

	/**
	 * 深拷贝对象
	 * @param source
	 * @param target
	 * @param <S>
	 * @param <T>
	 * @return
	 */
	public static <S, T> T deepCopy(S source, Class<T> target) {
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

		MapperFacade mapper = mapperFactory.getMapperFacade();

		T result = mapper.map(source, target);

		return result;
	}

	/**
	 * 深拷贝列表
	 * @param source
	 * @param target
	 * @param <S>
	 * @param <T>
	 * @return
	 */
	public static <S, T> List<T> deepCopyList(List<S> source, Class<T> target) {
		MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();

		return mapperFactory.getMapperFacade().mapAsList(source, target);
	}

}