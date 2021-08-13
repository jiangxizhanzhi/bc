package com.cyberpeace.oj.common.util;

import com.alibaba.fastjson.JSON;

/**
 * json序列化和反序列化工具类
 */
public class JsonUtil {

	private JsonUtil() {}

	public static String toJsonString(Object obj) {
		return JSON.toJSONString(obj);
	}
}
