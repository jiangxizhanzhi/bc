package com.cyberpeace.cr.crbase.utils;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.google.common.collect.Sets;

public class CrCommonValidator {

	/**
	 * 验证字符串长度。中文算2个字符。
	 */
	public static void validateLength(String target, Integer minLen, Integer maxLen,
			String errorCode) {
		if (target == null) {
			if (minLen == 0) {
				return;
			} else {
				throw new BusinessException(errorCode, minLen, maxLen);
			}
		}
		try {
			String targetStr = new String(target.getBytes("GBK"), "iso-8859-1");
			int len = targetStr.length();
			if (len < minLen || len > maxLen) {
				throw new BusinessException(errorCode, minLen, maxLen);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public static <T> void validateListIfEmpty(List<T> target, String errorCode) {
		if (target.isEmpty()) {
			throw new BusinessException(errorCode);
		}
	}

	public static void checkStringListDuplicate(List<String> target, String errorCode) {
		Set<String> stringSet = Sets.newHashSet(target);
		if (target.size() != stringSet.size()) {
			throw new BusinessException(errorCode);
		}
	}

	public static void checkStringExist(String target, String errorCode) {
		if (target == null || target.isEmpty()) {
			throw new BusinessException(errorCode);
		}
	}

	public static void validateScore(int target, int maxValue, String errorCode) {
		if (target < 0 || target > maxValue) {
			throw new BusinessException(errorCode);
		}
	}

	public static <T> void validateSize(List<T> target, int size, String errorCode) {
		if (target.size() != size) {
			throw new BusinessException(errorCode, size, target.size());
		}
	}

	public static <T> void validateSize(List<T> target, int min, int max, String errorCode) {
		if (target.size() < min || target.size() > max) {
			throw new BusinessException(errorCode);
		}
	}

	public static void validateScore(int target, int minValue, int maxValue, String errorCode) {
		if (target < minValue || target > maxValue) {
			throw new BusinessException(errorCode);
		}
	}

	/**
	 * 默认 名称长度 1~255
	 */
	public static void validateNameLen(String target, String errorCode) {
		validateLength(target, CrCommonConstants.MIN_STR_LEN, CrCommonConstants.MAX_NAME_STR_LEN,
				errorCode);
	}

	/**
	 * 默认描述长度 1~2048
	 */
	public static void validateDescLen(String target, String errorCode) {
		validateLength(target, CrCommonConstants.MIN_STR_LEN, CrCommonConstants.MAX_DESC_LEN,
				errorCode);
	}
}
