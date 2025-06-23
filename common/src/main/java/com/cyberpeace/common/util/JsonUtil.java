package com.cyberpeace.common.util;

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
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

/**
 * json序列化和反序列化工具类
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 该代码是一个用于 JSON 序列化和反序列化的工具类 `JsonUtil`，以下是对其功能和结构的简要分析： 
 * ### 功能概述： 
 * 1. **JSON 序列化**： 
 * - 提供将对象、集合、输入流等转换为 JSON 字符串的功能。 
 * - 支持自定义命名策略（PropertyNamingStrategy）。 
 * 2. **JSON 反序列化**： 
 * - 将 JSON 字符串或输入流解析为对象、对象数组或 JSON 对象。 
 * - 支持指定目标类类型和命名策略。 
 * 3. **特殊处理**： 
 * - 空值处理：如空对象返回 `{}`，空字符串返回 `[]`。 
 * - 输入流读取并解析 JSON 数据。 
 * - JSON 字段重命名功能。 
 * 4. **比较与转换**： 
 * - 比较两个 JSON 对象是否相等。 
 * - 将 Java 对象转换为 JSON 对象或 JSON 字符串。 
 * - 将 JSON 对象转换为有序的 `TreeMap`。 
 * ### 主要方法分类： 
 * #### 1. JSON 序列化 
 * - `toJsonString(Object obj)`：将对象转换为 JSON 字符串。 
 * - `arrayToString(List<T> objArr)`：将列表转换为 JSON 数组字符串。 
 * - `toJsonString(Object obj, PropertyNamingStrategy namingStrategy)`：支持命名策略的 JSON 序列化。 
 * #### 2. JSON 反序列化 
 * - `toJsonObjectArray(String text, Class<T> clazz)`：将 JSON 字符串解析为对象数组。 
 * - `toJsonObject(String text, Class<T> clazz)`：将 JSON 字符串解析为目标类的对象。 
 * - `toJsonObject(InputStream inputStream, Class<T> clazz)`：从输入流中读取并解析 JSON 数据。 
 * - `toJsonObject(String text, TypeReference<T> type, Feature... features)`：灵活的反序列化方法。 
 * #### 3. 特殊功能 
 * - `filedConvert(JSONObject jsonObject, String mapping)`：根据映射规则转换 JSON 的字段名称。 
 * - `java2JsonObject(T source)`：将 Java 对象转换为 JSON 对象。 
 * - `equalJson(JSONObject json1, JSONObject json2)`：比较两个 JSON 对象是否相等。 
 * - `toTreeMap(JSONObject jsonObject)`：将 JSON 对象转换为有序的 `TreeMap`。 
 * ### 技术细节： 
 * - 使用了阿里巴巴的 FastJSON 库进行 JSON 操作。 
 * - 引入了 Guava 和 Apache Commons Lang 工具类以增强功能。 
 * - 使用 Lombok 的 `@Slf4j` 注解简化日志记录。 
 * - 方法设计考虑了空值处理和异常处理（如输入流读取失败抛出业务异常）。 
 * ### 总结： 
 * 该工具类提供了全面的 JSON 操作功能，适用于多种场景，包括序列化、反序列化、字段重命名、比较和格式化输出。代码结构清晰，注释详尽，适合作为通用的 JSON 工具类使用。 
**/
@Slf4j
public class JsonUtil {

	/**
	 * JSON 实用程序
	 */
	private JsonUtil() {}

	/**
	 * 转换为 JSON 字符串
	 *
	 * @param obj 对象
	 * @return {@code String }
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
	 * @param text  发短信
	 * @param clazz 克拉兹
	 * @return {@code List<T> }
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
	 * @param text  发短信
	 * @param clazz 克拉兹
	 * @return {@code T }
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
            try {
                bR.close();
            } catch (IOException e) {
                log.error("Failed to close BufferedReader", e);
            }
        }
		return JSONObject.parseObject(responseStrBuilder.toString(), clazz);
	}

	/**
	 * 集合转换为字符串
	 *
	 * @param objArr
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
	 *
	 * @param text
	 * @return
	 */
	public static JSONArray stringToArray(String text) {
		if (StringUtils.isBlank(text)) {
			return new JSONArray();
		}
		return JSONArray.parseArray(text);
	}

	/**
	 * 转 JSON tObject
	 *
	 * @param text     发短信
	 * @param type     类型
	 * @param features 特征
	 * @return {@code T }
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

