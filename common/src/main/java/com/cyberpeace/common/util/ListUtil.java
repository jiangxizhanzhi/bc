package com.cyberpeace.common.util;

import java.util.List;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.google.common.collect.Lists;

public class ListUtil {

	private ListUtil() {}

	public static String list2String(List<String> list) {
		if (CollectionUtils.isEmpty(list)) {
			return "";
		}

		StringBuilder stringBuilder = new StringBuilder();
		for (String item : list) {
			stringBuilder.append(item.trim()).append(",");
		}

		return stringBuilder.toString();
	}

	public static List<String> string2List(String str) {
		List<String> ret = Lists.newArrayList();

		if (StringUtils.isBlank(str)) {
			return ret;
		}

		String[] ss = StringUtils.split(str, ",");
		for (String item : ss) {
			ret.add(item.trim());
		}

		return ret;
	}

	/**
	 * 截取列表拼接成字符串
	 * @param start
	 * @param end
	 * @return
	 */
	public static <T> String joinList(List<T> objList, int start, int end) {

		List<T> subList = objList.subList(start, end);
		StringBuilder builder = new StringBuilder();

		for (T obj : subList) {
			builder.append(
					JSONObject.toJSONString(obj, SerializerFeature.WriteNonStringValueAsString));
		}

		return builder.toString().trim();
	}
}
