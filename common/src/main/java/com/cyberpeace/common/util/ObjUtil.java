/**
 * ### 代码分析 
 * #### 功能概述 
 * 该代码提供了一个工具类 `ObjUtil`，用于操作 Java 对象的字段和属性。它主要支持以下功能： 
 * 1. 将对象的字段及其值转换为 `TreeMap` 或普通 `Map`。 
 * 2. 设置对象指定字段的值。 
 * 3. 获取对象指定字段的值。 
 * 4. 获取对象的所有字段。 
 * #### 主要方法解析 
 * 1. **`obj2map(Object obj)`** 
 * - 将对象的字段名和值转换为 `TreeMap`，不过滤值为 `null` 的字段。 
 * - 使用反射获取对象的所有字段，并通过 `getFieldValueByName` 方法获取每个字段的值。 
 * 2. **`obj2TreemapExcludeEmpty(Object obj)`** 
 * - 将对象的字段名和值转换为 `TreeMap`，但会过滤掉值为 `null` 或空字符串的字段。 
 * - 逻辑与 `obj2map` 类似，但在添加到结果时增加了非空检查。 
 * 3. **`setCertainFieldValue(Object obj, String fieldName, Object value)`** 
 * - 设置对象中指定字段的值。 
 * - 使用反射找到目标字段并调用 `set` 方法修改其值。 
 * - 如果字段不可访问，则抛出运行时异常。 
 * 4. **`setFieldValue(Object obj, String fieldName, String value)`** 
 * - 设置对象中指定字段的值，值被强制转换为字符串。 
 * - 逻辑与 `setCertainFieldValue` 类似，但直接传入字符串值。 
 * 5. **`getCertainFieldValue(Object obj, String fieldName)`** 
 * - 获取对象中指定字段的值，并将其转换为字符串返回。 
 * - 如果字段不存在或值为空，则返回 `null`。 
 * - 抛出异常表示字段未找到。 
 * 6. **`getFieldValueByName(String fieldName, Object obj)`** 
 * - 根据字段名获取对象的值。 
 * - 首先尝试通过 `getter` 方法获取值（如 `getXXX` 或 `isXXX`），如果找不到则记录错误日志并抛出异常。 
 * 7. **`getFields(Object obj)` 和 `getFields(Class<?> target)`** 
 * - 获取对象或类的所有字段，包括父类中的字段。 
 * - 使用递归方式从当前类获取所有声明的字段，并将它们合并到一个数组中返回。 
 * #### 特点 
 * - 使用了反射机制来操作对象的字段。 
 * - 支持过滤空值字段，提高数据的整洁性。 
 * - 提供了多种获取和设置字段值的方式，适应不同的需求场景。 
 * - 日志记录和异常处理较为完善，便于调试和维护。 
 * #### 注意事项 
 * - 依赖于 `guava` 和 `fastjson` 库，确保项目中已正确引入这些依赖。 
 * - 反射操作可能会带来性能开销，不适合频繁调用。 
 * - 对于复杂对象（包含嵌套对象或集合），此工具类可能无法完全处理，需扩展功能或使用更高级的序列化工具。 
 * #### 总结 
 * `ObjUtil` 是一个实用的工具类，适用于简单的对象字段操作。它提供了丰富的功能来满足基本的需求，但在处理复杂对象时可能需要额外的逻辑补充。 
**/
package com.cyberpeace.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 本工具类只针对简单对象处理。
 * 简单对象的定义为：对象每一个field对应的value为基本数据类型。
 * 基本数据类型参考java基本数据类型定义。
 *
 * @author SN
 * @date 2024/11/08
 */
public class ObjUtil {

	/**
	 * 日志
	 */
	private static final Logger LOG = LoggerFactory.getLogger(ObjUtil.class);

	/**
	 * obj util
	 */
	private ObjUtil() {}

	/**
	 * 将object按field转换成treeMap集合，其中key为field的名称，value为field的值。
	 * 不会过滤value为null的值。
	 *
	 * @param obj
	 * @return
	 */
	public static Map<String, Object> obj2map(Object obj) {
		if (null == obj) {
			return Maps.newTreeMap();
		}

		if (obj instanceof Map) {
			return (Map) obj;
		}

		Field[] fields = getFields(obj);

		Map<String, Object> result = Maps.newTreeMap();

		for (int i = 0; i < fields.length; i++) {
			result.put(fields[i].getName(), getFieldValueByName(fields[i].getName(), obj));
		}

		return result;
	}

	/**
	 * object转换成treemap。
	 * 如果object的某个field值为空，该值不会在map中返回。
	 *
	 * @param obj
	 * @return
	 */
	public static TreeMap<String, Object> obj2TreemapExcludeEmpty(Object obj) {
		TreeMap<String, Object> result = Maps.newTreeMap();

		if (null == obj) {
			return result;
		}

		Field[] fields = getFields(obj);

		for (int i = 0; i < fields.length; i++) {
			Object value = getFieldValueByName(fields[i].getName(), obj);

			if (null == value || StringUtils.isBlank(value.toString())) {
				continue;
			}

			result.put(fields[i].getName(), value);
		}

		return result;
	}

	/**
	 * 设置对象指定field的值
	 *
	 * @param obj       要修改的对象
	 * @param fieldName 对象要设置值的field
	 * @param value     需要设置的值
	 */
	public static void setCertainFieldValue(Object obj, String fieldName, Object value) {
		Field[] fields = getFields(obj);

		for (int i = 0; i < fields.length; i++) {
			if (!StringUtils.equals(fields[i].getName(), fieldName)) {
				continue;
			}

			fields[i].setAccessible(true);
			try {
				fields[i].set(obj, value);
			} catch (IllegalAccessException e) {
				throw new RuntimeException("ObjUtil.setCertainFeildValue obj=["
						+ JSON.toJSONString(obj) + "], field=[" + fieldName + "] error");
			}

			return;
		}
	}

	/**
	 * 设置田值
	 *
	 * @param obj       对象
	 * @param fieldName 田名称
	 * @param value     值
	 * @throws IllegalAccessException 非法访问异常
	 */
	public static void setFieldValue(Object obj, String fieldName, String value)
			throws IllegalAccessException {
		Field[] fields = getFields(obj);

		for (int i = 0; i < fields.length; i++) {
			if (!StringUtils.equals(fields[i].getName(), fieldName)) {
				continue;
			}

			fields[i].setAccessible(true);
			fields[i].set(obj, value);

			return;
		}
	}

	/**
	 * 获取对象中指定field的值，值以toString方法格式化后返回。
	 * 如果值不存在，返回null
	 *
	 * @param obj
	 * @param fieldName
	 * @return
	 */
	public static String getCertainFieldValue(Object obj, String fieldName) {
		Field[] fields = getFields(obj);

		for (int i = 0; i < fields.length; i++) {
			if (!StringUtils.equals(fields[i].getName(), fieldName)) {
				continue;
			}

			Object value = getFieldValueByName(fields[i].getName(), obj);

			if (null == value || StringUtils.isBlank(value.toString())) {
				return null;
			} else {
				return value.toString();
			}
		}

		throw new RuntimeException("ObjUtil.getCertainFieldValue obj=[" + JSON.toJSONString(obj)
				+ "], field=[" + fieldName + "] not found");
	}

	/**
	 * 获取田值由名称
	 *
	 * @param fieldName 田名称
	 * @param obj       对象
	 * @return {@code Object }
	 */
	public static Object getFieldValueByName(String fieldName, Object obj) {
		String firstLetter = fieldName.substring(0, 1).toUpperCase();
		try {
			String getter = "get" + firstLetter + fieldName.substring(1);
			Method method = obj.getClass().getMethod(getter, new Class[] {});

			return method.invoke(obj, new Object[] {});
		} catch (NoSuchMethodException e) {
			// 如果是方法未找到，可能变量是boolean类型，用is重新尝试一把
			try {
				String getter = "is" + firstLetter + fieldName.substring(1);
				Method method = obj.getClass().getMethod(getter, new Class[] {});

				return method.invoke(obj, new Object[] {});
			} catch (Exception e1) {
				LOG.error("ObjUtil.getFieldValueByName error, obj=[" + obj + "],fieldName=["
						+ fieldName + "]", e);
				throw new RuntimeException(
						"ObjUtil.getFieldValueByName error，fieldName=" + fieldName);
			}
		} catch (Exception e) {
			LOG.error("ObjUtil.getFieldValueByName error, obj=[" + obj + "],fieldName=[" + fieldName
					+ "]", e);
			throw new RuntimeException("ObjUtil.getFieldValueByName error");
		}
	}

	/**
	 * 获取领域
	 *
	 * @param obj 对象
	 * @return {@code Field[] }
	 */
	public static Field[] getFields(Object obj) {
		Class<?> cls = obj.getClass();
		return getFields(cls);
	}

	/**
	 * 获取领域
	 *
	 * @param target 目标
	 * @return {@code Field[] }
	 */
	public static Field[] getFields(Class<?> target) {

		Class<?> cls = target;
		List<Field[]> result = Lists.newArrayList();
		Field[] fs = cls.getDeclaredFields();

		do {
			result.add(0, fs);
			cls = cls.getSuperclass();
			fs = cls.getDeclaredFields();
		} while (null != fs && fs.length > 0);

		int size = 0;
		for (int i = 0; i < result.size(); i++) {
			size += result.get(i).length;
		}

		Field[] ret = new Field[size];
		int index = 0;
		for (int i = 0; i < result.size(); i++) {
			for (int j = 0; j < result.get(i).length; j++) {
				ret[index++] = result.get(i)[j];
			}
		}

		return ret;

	}
}
