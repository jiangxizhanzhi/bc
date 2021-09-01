package com.cyberpeace.common.util;

import com.cyberpeace.common.exception.InvalidParamException;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.List;
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
}
