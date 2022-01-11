package com.cyberpeace.common.util;

import java.util.List;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cyberpeace.common.constant.CommonConstant;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.Feature;
import com.google.common.collect.Lists;

/**
 * json序列化和反序列化工具类
 */
public class JsonUtil {

	private JsonUtil() {}

	public static String toJsonString(Object obj) {
		if (null == obj) {
			return "{}";
		}

		if (obj instanceof String) {
			return (String) obj;
		}

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
		if (null == objArr) {
			return "[]";
		}
		return JSONArray.toJSONString(objArr, SerializerFeature.WriteNullStringAsEmpty);
	}

	/**
	 * 字符串转为jsonArray
	 * @param text
	 * @return
	 */
	public static JSONArray StringToArray(String text) {
		return JSONArray.parseArray(text);
	}

	public static <T> T toJsonTObject(String text, TypeReference<T> type, Feature... features) {
		return JSON.parseObject(text, type, features);

	}

	/**
	 * json key转换
	 * @param jsonObject 原json数据
	 * @param mapping 原json的key与新key的映射关系
	 * @return
	 */
	public static JSONObject filedConvert(JSONObject jsonObject, String mapping) {

		JSONObject toJsonObject = JsonUtil.toJsonObject(mapping, JSONObject.class);
		JSONObject convertValue = new JSONObject();

		// 遍历旧数据 转换key值
		for (String key : jsonObject.keySet()) {
			String newKetStr = toJsonObject.getString(key);
			if (StringUtils.isNotBlank(newKetStr)) {
				// 因为部分字段可能会存在一对多关系 比如课程导入 课程编号同时对应resource_id和course_num
				String[] newKeys = newKetStr.split(CommonConstant.CODE_COMMA);

				for (String newKey : newKeys) {
					convertValue.put(newKey, jsonObject.getString(key));
				}
			}
		}

		return convertValue;
	}

}
