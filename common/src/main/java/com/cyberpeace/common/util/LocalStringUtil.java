package com.cyberpeace.common.util;

import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.exception.InvalidParamException;
import com.google.common.collect.Lists;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class LocalStringUtil {

	private LocalStringUtil() {}

	public static String notEmptyString(String s, String fieldName) {
		if (s == null) {
			return null;
		}

		s = s.trim();
		if (s.isEmpty()) {
			throw new InvalidParamException(fieldName);
		}

		return s;
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
							if (!Character.isUpperCase(next) && Character.isAlphabetic(next)) {
								if (lastEntry != '_') {
									result.append('_');
								}
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
		return matchStr.get(CommonConstant.ZERO);
	}
}
