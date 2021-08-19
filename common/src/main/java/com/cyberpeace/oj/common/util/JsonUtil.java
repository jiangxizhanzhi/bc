package com.cyberpeace.oj.common.util;

import java.util.List;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * json序列化和反序列化工具类
 */
public class JsonUtil {

	private JsonUtil() {}

	public static String toJsonString(Object obj) {
		return JSON.toJSONString(obj);
	}

	public static <T> List<T> toJsonObjectArray(String text, Class<T> clazz) {
		return JSONObject.parseArray(text, clazz);
	}

}
