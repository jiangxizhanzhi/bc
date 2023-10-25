package com.cyberpeace.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.*;
import com.alibaba.fastjson.parser.Feature;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

/**
 * json序列化和反序列化工具类
 */
@Slf4j
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

		return JSON.parseArray(text, clazz);
	}

	public static <T> T toJsonObject(String text, Class<T> clazz) {
		return JSON.parseObject(text, clazz);
	}

	@SneakyThrows
	public static <T> T toJsonObject(InputStream inputStream, Class<T> clazz) {

		BufferedReader bR = new BufferedReader(
				new InputStreamReader(inputStream, StandardCharsets.UTF_8));
		String line = "";
		StringBuilder responseStrBuilder = new StringBuilder();
		try {
			while ((line = bR.readLine()) != null) {
				responseStrBuilder.append(line);
			}
		} catch (IOException e) {
			log.error("InputStream parse failed!", e);
			throw new BusinessException(ErrorCode.FILE_READ_FAIL);
		} finally {
			bR.close();
		}
		return JSON.parseObject(responseStrBuilder.toString(), clazz);
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
		return JSON.toJSONString(objArr, SerializerFeature.WriteNullStringAsEmpty);
	}

	/**
	 * 字符串转为jsonArray
	 * @param text
	 * @return
	 */
	public static JSONArray stringToArray(String text) {
		return JSON.parseArray(text);
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

	/**
	 * 将 java 对象转为 JSONObject
	 * @param source
	 * @param <T>
	 * @return
	 */
	public static <T> JSONObject java2JsonObject(T source) {
		return (JSONObject) JSON.toJSON(source);
	}

	/**
	 * @param namingStrategy 命名策略
	 */
	public static <T> JSONObject java2JsonObject(T source, PropertyNamingStrategy namingStrategy) {
		SerializeConfig config = new SerializeConfig();
		config.setPropertyNamingStrategy(namingStrategy);
		return (JSONObject) JSON.toJSON(source, config);
	}

	/**
	 * 支持命名策略，转化实例为json字符串
	 * @param obj
	 * @param namingStrategy
	 * @return
	 */
	public static String toJsonString(Object obj, PropertyNamingStrategy namingStrategy) {
		if (null == obj) {
			return "{}";
		}

		if (obj instanceof String) {
			return (String) obj;
		}

		SerializeConfig config = new SerializeConfig();
		config.setPropertyNamingStrategy(namingStrategy);

		return JSON.toJSONString(obj, config);
	}

	/**
	 * 集合转换为字符串
	 * @param objArr
	 * @param <T>
	 * @return
	 */
	public static <T> String arrayToString(List<T> objArr, PropertyNamingStrategy namingStrategy) {
		if (null == objArr) {
			return "[]";
		}
		SerializeConfig config = new SerializeConfig();
		config.setPropertyNamingStrategy(namingStrategy);
		return JSON.toJSONString(objArr, config, SerializerFeature.WriteNullStringAsEmpty);
	}

	/**
	 * 比较两个Json数据
	 * @param json1
	 * @param json2
	 * @return
	 */
	public static boolean equalJson(JSONObject json1, JSONObject json2) {

		if (json1 == null || json2 == null) {
			return CommonConstant.FALSE;
		}

		TreeMap<String, String> treeMap1 = toTreeMap(json1);
		TreeMap<String, String> treeMap2 = toTreeMap(json2);

		return JsonUtil.toJsonString(treeMap1).equals(JsonUtil.toJsonString(treeMap2));

	}

	/** 获取有顺序的key*/
	private static TreeMap<String, String> toTreeMap(JSONObject jsonObject) {

		TreeMap<String, String> treeMap = Maps.newTreeMap();
		Iterator<String> iterator = jsonObject.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			treeMap.put(key, jsonObject.getString(key));
		}

		return treeMap;
	}

}
