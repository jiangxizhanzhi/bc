package com.cyberpeace.common.util;

import java.util.List;

import com.alibaba.fastjson.JSONArray;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;

/**
 * json序列化和反序列化工具类
 */
public class JsonUtil {

	private JsonUtil() {}

	public static String toJsonString(Object obj) {
		return JSON.toJSONString(obj);
	}

	public static <T> List<T> toJsonObjectArray(String text, Class<T> clazz) {
		if (StringUtils.isBlank(text)) {
			return Lists.newArrayList();
		}

		return JSONObject.parseArray(text, clazz);
	}

	public static <T> T toJsonObject(String text, Class<T> clazz) {
		return JSONObject.parseObject(text, clazz);
	}

	/**
	 * 集合转换为字符串
	 * @param objArr
	 * @param <T>
	 * @return
	 */
	public static <T> String arrayToString(List<T> objArr) {
		return JSONArray.toJSONString(objArr);
	}
}
