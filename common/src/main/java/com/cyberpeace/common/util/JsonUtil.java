package com.cyberpeace.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
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
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/07
 * @since 1.0.0
 */
@Slf4j
public class JsonUtil {

	/**
	 * JSON 实用程序
	 *
	 * @throws
	 */
	private JsonUtil() {}

	/**
	 * 转换为 JSON 字符串
	 *
	 * @param obj 对象
	 * @return {@code String }
	 * @throws
	 * @see Object
	 * @see String
	 */
	public static String toJsonString(Object obj) {
		if (null == obj) {
			return "{}";
		}

		if (obj instanceof String) {
			return (String) obj;
		}

		return JSON.toJSONString(obj);
	}

	/**
	 * 到 JSON 对象数组
	 *
	 * @param text  文本
	 * @param clazz 克拉兹
	 * @return {@code List<T> }
	 * @throws
	 * @see String
	 * @see Class<T>
	 * @see List<T>
	 */
	public static <T> List<T> toJsonObjectArray(String text, Class<T> clazz) {
		if (StringUtils.isBlank(text)) {
			return Lists.newArrayList();
		}

		return JSONObject.parseArray(text, clazz);
	}

	/**
	 * 到 JSON 对象
	 *
	 * @param text  文本
	 * @param clazz 克拉兹
	 * @return {@code T }
	 * @throws
	 * @see String
	 * @see Class<T>
	 * @see T
	 */
	public static <T> T toJsonObject(String text, Class<T> clazz) {
		return JSONObject.parseObject(text, clazz);
	}

	/**
	 * 到 JSON 对象
	 *
	 * @param inputStream 输入流
	 * @param clazz       克拉兹
	 * @return {@code T }
	 * @throws
	 * @see InputStream
	 * @see Class<T>
	 * @see T
	 */
	@SneakyThrows
	public static <T> T toJsonObject(InputStream inputStream, Class<T> clazz) {

		BufferedReader bR = new BufferedReader(new InputStreamReader(inputStream, "utf-8"));
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
		return JSONObject.parseObject(responseStrBuilder.toString(), clazz);
	}

	/**
	 * 集合转换为字符串
	 *
	 * @param objArr
	 * @return
	 * @throws
	 * @see List<T>
	 * @see String
	 */
	public static <T> String arrayToString(List<T> objArr) {
		if (null == objArr) {
			return "[]";
		}
		return JSONArray.toJSONString(objArr, SerializerFeature.WriteNullStringAsEmpty);
	}

	/**
	 * 字符串转为jsonArray
	 *
	 * @param text
	 * @return
	 * @throws
	 * @see String
	 * @see JSONArray
	 */
	public static JSONArray stringToArray(String text) {
		return JSONArray.parseArray(text);
	}

	/**
	 * 转 JSON tObject
	 *
	 * @param text     文本
	 * @param type     类型
	 * @param features 特征
	 * @return {@code T }
	 * @throws
	 * @see String
	 * @see TypeReference<T>
	 * @see Feature...
	 * @see T
	 */
	public static <T> T toJsonTObject(String text, TypeReference<T> type, Feature... features) {
		return JSON.parseObject(text, type, features);

	}

	/**
	 * json key转换
	 *
	 * @param jsonObject 原json数据
	 * @param mapping    原json的key与新key的映射关系
	 * @return
	 * @throws
	 * @see JSONObject
	 * @see String
	 * @see JSONObject
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
	 *
	 * @param source
	 * @return
	 * @throws
	 * @see T
	 * @see JSONObject
	 */
	public static <T> JSONObject java2JsonObject(T source) {
		return (JSONObject) JSONObject.toJSON(source);
	}

	/**
	 * Java2 JSON 对象
	 *
	 * @param source         源
	 * @param namingStrategy 命名策略
	 * @return {@code JSONObject }
	 * @throws
	 * @see T
	 * @see PropertyNamingStrategy
	 * @see JSONObject
	 */
	public static <T> JSONObject java2JsonObject(T source, PropertyNamingStrategy namingStrategy) {
		SerializeConfig config = new SerializeConfig();
		config.setPropertyNamingStrategy(namingStrategy);
		return (JSONObject) JSONObject.toJSON(source, config);
	}

	/**
	 * 支持命名策略，转化实例为json字符串
	 *
	 * @param obj
	 * @param namingStrategy
	 * @return
	 * @throws
	 * @see Object
	 * @see PropertyNamingStrategy
	 * @see String
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
	 *
	 * @param objArr
	 * @param namingStrategy 命名策略
	 * @return
	 * @throws
	 * @see List<T>
	 * @see PropertyNamingStrategy
	 * @see String
	 */
	public static <T> String arrayToString(List<T> objArr, PropertyNamingStrategy namingStrategy) {
		if (null == objArr) {
			return "[]";
		}
		SerializeConfig config = new SerializeConfig();
		config.setPropertyNamingStrategy(namingStrategy);
		return JSONArray.toJSONString(objArr, config, SerializerFeature.WriteNullStringAsEmpty);
	}

	/**
	 * 比较两个Json数据
	 *
	 * @param json1
	 * @param json2
	 * @return
	 * @throws
	 * @see JSONObject
	 * @see JSONObject
	 * @see boolean
	 */
	public static boolean equalJson(JSONObject json1, JSONObject json2) {

		if (json1 == null || json2 == null) {
			return CommonConstant.FALSE;
		}

		TreeMap<String, String> treeMap1 = toTreeMap(json1);
		TreeMap<String, String> treeMap2 = toTreeMap(json2);

		return JsonUtil.toJsonString(treeMap1).equals(JsonUtil.toJsonString(treeMap2));

	}

	/**
	 * 获取有顺序的key
	 *
	 * @param jsonObject JSON 对象
	 * @return {@code TreeMap<String, String> }
	 * @throws
	 * @see JSONObject
	 * @see TreeMap<String, String>
	 */
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
