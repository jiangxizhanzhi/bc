package com.cyberpeace.cr.crbase.utils;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.constant.BaseI18nErrorMsg;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.google.common.collect.Sets;

public class CrCommonValidator {

	/**
	 * 验证字符串长度。中文算2个字符。
	 */
	public static void validateLength(String target, Integer minLen, Integer maxLen, BaseI18nErrorMsg i18nErrorMsg) {
		try {
			String targetStr = new String(target.getBytes("GBK"), "iso-8859-1");
			int len = targetStr.length();
			if (len < minLen || len > maxLen) {
				throw new BusinessException(i18nErrorMsg.parameterize(minLen, maxLen));
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public static <T> void validateListIfEmpty(List<T> target, BaseI18nErrorMsg i18nErrorMsg) {
		if (target.isEmpty()) {
			throw new BusinessException(i18nErrorMsg);
		}
	}

	public static void checkStringListDuplicate(List<String> target, BaseI18nErrorMsg i18nErrorMsg) {
		Set<String> stringSet = Sets.newHashSet(target);
		if (target.size() != stringSet.size()) {
			throw new BusinessException(i18nErrorMsg);
		}
	}

	public static void checkStringExist(String target, BaseI18nErrorMsg i18nErrorMsg) {
		if (target == null || target.isEmpty()) {
			throw new BusinessException(i18nErrorMsg);
		}
	}

	public static void validateScore(int target, int maxValue, BaseI18nErrorMsg i18nErrorMsg) {
		if (target < 0 || target > maxValue) {
			throw new BusinessException(i18nErrorMsg);
		}
	}

	public static <T> void validateSize(List<T> target, int min, int max, BaseI18nErrorMsg i18nErrorMsg) {
		if (target.size() < min || target.size() > max) {
			throw new BusinessException(i18nErrorMsg.parameterize(min, max));
		}
	}

	public static void validateScore(int target, int minValue, int maxValue, BaseI18nErrorMsg i18nErrorMsg) {
		if (target < minValue || target > maxValue) {
			throw new BusinessException(i18nErrorMsg);
		}
	}

	/**
	 * 默认 名称长度 1~255
	 */
	public static void validateNameLen(String target, BaseI18nErrorMsg i18nErrorMsg) {
		validateLength(target, CrCommonConstants.MIN_STR_LEN, CrCommonConstants.MAX_NAME_STR_LEN, i18nErrorMsg);
	}

}
