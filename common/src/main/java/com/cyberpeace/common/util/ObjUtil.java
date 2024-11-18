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
 * @version 1.0.0
 * @date 2024/11/18
 * @since 1.0.0
 */
public class ObjUtil {

	/**
	 * 日志
	 *
	 * @see Logger
	 */
	private static final Logger LOG = LoggerFactory.getLogger(ObjUtil.class);

	/**
	 * obj util
	 *
	 * @throws
	 */
	private ObjUtil() {}

	/**
	 * 将object按field转换成treeMap集合，其中key为field的名称，value为field的值。
	 * 不会过滤value为null的值。
	 *
	 * @param obj
	 * @return
	 * @throws
	 * @see Object
	 * @see Map<String, Object>
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
	 * @throws
	 * @see Object
	 * @see TreeMap<String, Object>
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
	 * @throws
	 * @see Object
	 * @see String
	 * @see Object
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
	 * @throws @throws IllegalAccessException 非法访问异常
	 * @see Object
	 * @see String
	 * @see String
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
	 * @throws
	 * @see Object
	 * @see String
	 * @see String
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
	 * @throws
	 * @see String
	 * @see Object
	 * @see Object
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
	 * @throws
	 * @see Object
	 * @see Field[]
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
	 * @throws
	 * @see Class<?>
	 * @see Field[]
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
