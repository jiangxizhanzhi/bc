package com.cyberpeace.common.util;

import java.util.List;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.impl.DefaultMapperFactory;

/**
 * 对象拷贝工具类
 * @author yinqingrong
 */
/**
 * 这段代码是一个工具类 `BeanUtils`，用于对象之间的属性拷贝。以下是代码的主要功能和特点： 
 * 1. **浅拷贝**： 
 * - 提供了 `copy` 方法，使用 `Spring` 的 `BeanUtils.copyProperties` 方法实现对象属性的浅拷贝。 
 * - 支持泛型，可以拷贝任意类型的对象。 
 * 2. **深拷贝**： 
 * - 使用 `Orika` 库的 `MapperFacade` 实现对象的深拷贝。 
 * - 提供了 `deepCopy` 方法用于单个对象的深拷贝。 
 * - 提供了 `deepCopyList` 方法用于列表的深拷贝。 
 * 3. **私有构造函数**： 
 * - 类被设计为工具类，不允许实例化，因此构造函数被私有化。 
 * 4. **依赖库**： 
 * - 依赖于 `Spring` 的 `BeanUtils` 和 `Orika` 的 `MapperFacade`。 
 * 总结：该工具类提供了对象属性的浅拷贝和深拷贝功能，支持泛型，适用于不同类型的对象拷贝需求。 
**/
public class BeanUtils {

	public static final MapperFacade mapper = new DefaultMapperFactory.Builder().build().getMapperFacade();

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
        return mapper.map(source, target);
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
		return mapper.mapAsList(source, target);
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
