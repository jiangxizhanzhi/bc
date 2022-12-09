package com.cyberpeace.common.util;

import lombok.extern.slf4j.Slf4j;
import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;

import java.util.List;

/**
 * 对象拷贝工具类
 * @author yinqingrong
 */
@Slf4j
public class BeanUtils {

	private BeanUtils() {}

	/**
	 * 浅拷贝
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

		T result;
		try {
			result = mapper.map(source, target);
		}
		catch (Exception e){
			log.error("source:" + source.toString());
			log.error("target:" + target);
			throw e;
		}

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

	/**
	 * 拷贝对象属性
	 * @param source
	 * @param target
	 * @return
	 */
	public static void copyProperties(Object source, Object target){
		org.springframework.beans.BeanUtils.copyProperties(source, target);
	}

}