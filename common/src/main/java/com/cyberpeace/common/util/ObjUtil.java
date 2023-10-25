package com.cyberpeace.common.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

import com.cyberpeace.common.exception.BusinessException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.util.ReflectionUtils;

/**
 * 本工具类只针对简单对象处理。
 * 简单对象的定义为：对象每一个field对应的value为基本数据类型。
 * 基本数据类型参考java基本数据类型定义。
 */
public class ObjUtil {

	private static final Logger LOG = LoggerFactory.getLogger(ObjUtil.class);

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
	public static SortedMap<String, Object> obj2TreemapExcludeEmpty(Object obj) {
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

			ReflectionUtils.makeAccessible(fields[i]);
			ReflectionUtils.setField(fields[i],obj, value);
			return;
		}
	}

	public static void setFieldValue(Object obj, String fieldName, String value)
			 {
		Field[] fields = getFields(obj);

		for (int i = 0; i < fields.length; i++) {
			if (!StringUtils.equals(fields[i].getName(), fieldName)) {
				continue;
			}
			ReflectionUtils.makeAccessible(fields[i]);
			ReflectionUtils.setField(fields[i],obj, value);

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

		throw new BusinessException();
	}

	public static Object getFieldValueByName(String fieldName, Object obj) {
		String firstLetter = fieldName.substring(0, 1).toUpperCase();
		try {
			String getter = "get" + firstLetter + fieldName.substring(1);
			Method method = obj.getClass().getMethod(getter);

			return method.invoke(obj);
		} catch (NoSuchMethodException e) {
			// 如果是方法未找到，可能变量是boolean类型，用is重新尝试一把
			try {
				String getter = "is" + firstLetter + fieldName.substring(1);
				Method method = obj.getClass().getMethod(getter);

				return method.invoke(obj);
			} catch (Exception e1) {
				LOG.error("ObjUtil.getFieldValueByName error, obj=[" + obj + "],fieldName=["
						+ fieldName + "]", e);
				throw new BusinessException();
			}
		} catch (Exception e) {
			LOG.error("ObjUtil.getFieldValueByName error, obj=[" + obj + "],fieldName=[" + fieldName
					+ "]", e);
			throw new BusinessException();
		}
	}

	public static Field[] getFields(Object obj) {
		Class<?> cls = obj.getClass();
		return getFields(cls);
	}

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
