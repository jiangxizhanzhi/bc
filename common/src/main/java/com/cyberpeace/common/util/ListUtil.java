package com.cyberpeace.common.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.IntStream;

import com.alibaba.fastjson.JSON;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;
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
	 * 截取列表拼接成字符串 空字符传略过
	 * @param start
	 * @param end
	 * @return
	 */
	public static <T> String joinList(List<T> objList, int start, int end) {

		List<T> subList = objList.subList(start, end);
		StringBuilder builder = new StringBuilder();

		for (T obj : subList) {
			if (obj instanceof String) {
				String str = (String) obj;
				if (StringUtils.isBlank(str)) {
					continue;
				}
				builder.append(obj);
			} else {
				builder.append(JSONObject.toJSONString(obj));
			}
		}

		return builder.toString().trim();
	}

	public static <E> List<E> pageList(List<E> list, int page, int perPage, int listSize) {
		int startIndex = (page - 1) * perPage;
		int endIndex = page * perPage;
		endIndex = Math.min(endIndex, listSize);
		startIndex = Math.max(0, startIndex);
		if (startIndex >= endIndex) {
			return Lists.newArrayList();
		}
		return list.subList(startIndex, endIndex);
	}

	/**
	 * 比较两个不重复的列表是否包含的元素一致
	 * @param l1 
	 * @param l2
	 * @param <T>
	 * @return
	 */
	public static <T> Boolean compare2List(List<T> l1, List<T> l2) {
		if (l1.size() != l2.size()) {
			return false;
		}
		Set<T> set = new HashSet<>();
		set.addAll(l1);
		set.addAll(l2);
		return set.size() == l1.size();
	}

	/**
	 * 返回指定起止的整数列表
	 * @param start
	 * @param end
	 * @return
	 */
	public static List<Integer> listRangeInt(Integer start, Integer end) {
		return Lists.newArrayList(IntStream.rangeClosed(start, end).iterator());
	}

	/**
	 * 截取列表拼接成包括换行符的字符串 空字符传略过
	 * @param start
	 * @param end
	 * @return
	 */
	public static <T> String joinLineSeparatorList(List<T> objList, int start, int end) {

		List<T> subList = objList.subList(start, end);
		StringBuilder builder = new StringBuilder();

		for (T obj : subList) {
			if (obj instanceof String) {
				String str = (String) obj;
				if (StringUtils.isBlank(str)) {
					continue;
				}
				builder.append(obj).append("\n");
			} else {
				builder.append(JSON.toJSONString(obj)).append("\n");
			}
		}

		return builder.toString();
	}

}
