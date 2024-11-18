package com.cyberpeace.common.util;

import java.util.List;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * 对象拷贝工具类
 *
 * @author yinqingrong
 * @date 2024/11/07
 * @version 1.0.0
 * @since 1.0.0
 */
public class BeanUtils {

	/**
	 * 映射
	 *
	 * @see MapperFacade
	 */
	public static MapperFacade mapper = new DefaultMapperFactory.Builder().build().getMapperFacade();

	/**
	 * Bean 实用程序
	 *
	 * @throws
	 */
	private BeanUtils() {}

	/**
	 * 浅拷贝
	 *
	 * @param source
	 * @param target
	 * @return
	 * @throws
	 * @see S
	 * @see Class<T>
	 * @see T
	 */
	public static <S, T> T copy(S source, Class<T> target) {
		T result = org.springframework.beans.BeanUtils.instantiateClass(target);

		org.springframework.beans.BeanUtils.copyProperties(source, result);

		return result;
	}

	/**
	 * 深拷贝对象
	 *
	 * @param source
	 * @param target
	 * @return
	 * @throws
	 * @see S
	 * @see Class<T>
	 * @see T
	 */
	public static <S, T> T deepCopy(S source, Class<T> target) {
        return mapper.map(source, target);
	}

	/**
	 * 深拷贝列表
	 *
	 * @param source
	 * @param target
	 * @return
	 * @throws
	 * @see List<S>
	 * @see Class<T>
	 * @see List<T>
	 */
	public static <S, T> List<T> deepCopyList(List<S> source, Class<T> target) {
		return mapper.mapAsList(source, target);
	}

	/**
	 * 拷贝对象属性
	 *
	 * @param source
	 * @param target 目标
	 * @throws
	 * @see Object
	 * @see Object
	 */
	public static void copyProperties(Object source, Object target){
		org.springframework.beans.BeanUtils.copyProperties(source, target);
	}

}