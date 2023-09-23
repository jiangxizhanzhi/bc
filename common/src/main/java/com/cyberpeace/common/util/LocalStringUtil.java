package com.cyberpeace.common.util;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.cyberpeace.common.constant.CommonConstant;
import com.google.common.collect.Lists;

public class LocalStringUtil {

	private LocalStringUtil() {}

    /**
     * 前后增加换行符
     * @param value
     * @return
     */
    public static String frontBackLine(String value) {
        if (StringUtils.isBlank(value)) {
            value = CommonConstant.EMPTY_STRING;
        }
        return System.lineSeparator() + value + System.lineSeparator();
    }

	public static String snakeCaseFormat(String name) {
		final StringBuilder result = new StringBuilder();

		boolean lastUppercase = false;

		for (int i = 0; i < name.length(); i++) {
			char ch = name.charAt(i);
			char lastEntry = i == 0 ? 'X' : result.charAt(result.length() - 1);
			if (ch == ' ' || ch == '_' || ch == '-' || ch == '.') {
				lastUppercase = false;

				if (lastEntry == '_') {
					continue;
				} else {
					ch = '_';
				}
			} else if (Character.isUpperCase(ch)) {
				ch = Character.toLowerCase(ch);
				if (i > 0) {
					if (lastUppercase) {
						if (i + 1 < name.length()) {
							char next = name.charAt(i + 1);
							if (!Character.isUpperCase(next) && Character.isAlphabetic(next)
									&& lastEntry != '_') {
								result.append('_');
							}
						}
					} else {
						if (lastEntry != '_') {
							result.append('_');
						}
					}
				}
				lastUppercase = true;
			} else {
				lastUppercase = false;
			}

			result.append(ch);
		}
		return result.toString();
	}

	public static List<Integer> strToList(String data) {
		return Arrays.stream(StringUtils.split(data, ',')).map(Integer::parseInt)
				.collect(Collectors.toList());
	}

	public static List<String> strToListStr(String data) {
		return Arrays.stream(StringUtils.split(data, ',')).collect(Collectors.toList());
	}

	public static String strRemoveSubStr(String ids, String data) {
		List<String> strings = JsonUtil.toJsonObjectArray(ids, String.class);

		strings.remove(data);

		return JsonUtil.toJsonString(strings);

	}

	/**
	 * 根据正则表达是获取内容中的匹配数据
	 * @param content
	 * @param regex 正则表达式
	 * @return
	 */
	public static List<String> getMatchStr(String content, String regex) {

		List<String> strList = Lists.newArrayList();
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			strList.add(matcher.group());
		}
		return strList;
	}

	/**
	 * 根据正则表达是获取内容中url的匹配数据
	 * @param content
	 * @param regex 正则表达式集合
	 * @return
	 */
	public static List<String> getMatchUrlStr(String content, String regex) {

		List<String> strList = Lists.newArrayList();
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			strList.add(matcher.group(1).trim());
		}
		return strList;
	}

	/**
	 * 根据正则表达是获取内容中的匹配数据
	 * @param content 内容
	 * @param regex 正则表达式
	 * @param index 子匹配序号
	 * @return 匹配到的字符串列表
	 */
	public static List<String> getMatchStr(String content, String regex, Integer index) {

		List<String> strList = Lists.newArrayList();
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(content);

		while (matcher.find()) {
			strList.add(matcher.group(index).trim());
		}
		return strList;
	}

	/**
	 * 根据正则表达是获取内容中的匹配数据 仅匹配一个
	 * @param content
	 * @param regex
	 * @return
	 */
	public static String getFirstMatchStr(String content, String regex) {

		List<String> matchStr = getMatchStr(content, regex);

		if (CollectionUtils.isEmpty(matchStr)) {
			return "";
		}
		return matchStr.get(CommonConstant.ZERO).trim();
	}

	/**
	 * 根据正则表达是获取内容中的匹配数据 仅匹配一个
	 * @param content
	 * @param regex
	 * @param index 子匹配序号
	 * @return
	 */
	public static String getFirstMatchStr(String content, String regex, Integer index) {

		List<String> matchStr = getMatchStr(content, regex, index);

		if (CollectionUtils.isEmpty(matchStr)) {
			return CommonConstant.EMPTY_STRING;
		}
		return matchStr.get(CommonConstant.ZERO).trim();
	}
	/**
	 * 根据正则表达是获取内容中的匹配数据 匹配全部
	 * @param content
	 * @param regex
	 * @param index 子匹配序号
	 * @return
	 */
	public static List<String> getAllMatchStr(String content, String regex, Integer index) {

		List<String> matchStr = getMatchStr(content, regex, index);


		return matchStr;
	}
	/**
	 * 数据替换
	 * @param content
	 * @param old
	 * @param newValue
	 * @return
	 */
	public static String replaceAll(String content, List<String> old, List<String> newValue) {

		if (StringUtils.isBlank(content)) {
			return content;
		}

		if (old.size() > newValue.size()) {
			for (int i = 0; i < old.size() - newValue.size(); i++) {
				newValue.add("");
			}
		}

		for (int i = 0; i < old.size(); i++) {
			content = content.replace(old.get(i), newValue.get(i));
		}

		return content;
	}

	/**
	 * 获取随机长度字符串
	 * @param length
	 * @return
	 */
	public static String getRandomStr(int length) {
		String randomStr = IDGeneratorUtil.getID();
		if (randomStr.length() >= length) {
			return randomStr.substring(0, length);
		}

		return randomStr + getRandomStr(length - randomStr.length());
	}

	public static String replaceToBlank(String content, List<String> matchStr) {
		if (StringUtils.isBlank(content) || CollectionUtils.isEmpty(matchStr)) {
			return content;
		}

		for (int i = 0; i < matchStr.size(); i++) {
			content = content.replace(matchStr.get(i), "");
		}

		return content;
	}

	public static String transfer(String search) {
		String newPattern = search;

		if (newPattern.contains("_") && !newPattern.contains("\\_")) {
			newPattern = newPattern.replace("_", "\\_");
		}
		if (newPattern.contains("%") && !newPattern.contains("\\%")) {
			newPattern = newPattern.replace("%", "\\%");
		}
		if (newPattern.contains("\'") && !newPattern.contains("\\'")) {
			newPattern = newPattern.replace("\'", "\\'");
		}

		return newPattern;
	}
}
