package com.cyberpeace.cr.crbase.service.impl;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Set;

import com.cyberpeace.common.exception.BusinessException;
import com.google.common.collect.Sets;

public class CrCommonValidator {

	public static void validateLength(String target, Integer minLen, Integer maxLen,
			String errorCode) {
		try {
			String targetStr = new String(target.getBytes("GBK"), "iso-8859-1");
			int len = targetStr.length();
			if (len < minLen || len > maxLen) {
				throw new BusinessException(errorCode);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public static <T> void validateEmptyList(List<T> target, String errorCode) {
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
			throw new BusinessException(errorCode);
		}
	}

	public static void validateScore(int target, int minValue, int maxValue, String errorCode) {
		if (target < minValue || target > maxValue) {
			throw new BusinessException(errorCode);
		}
	}

}
