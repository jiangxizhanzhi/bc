package com.cyberpeace.oj.ojbase.utils;

import com.cyberpeace.common.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;

@Slf4j
public class OjCommonValidator {

	public static void validateRange(int target, int minValue, int maxValue, String errorCode) {
		if (target < minValue || target > maxValue) {
			log.error("target = [{}], minValue = [{}], maxValue = [{}], target < minValue || target > maxValue", target, minValue, maxValue);
			throw new BusinessException(errorCode, minValue, maxValue);
		}
	}

	/**
	 * 验证字符串长度。中文算2个字符。
	 */
	public static void validateLength(String target, Integer minLen, Integer maxLen,
									  String errorCode) {
		if (target == null) {
			if (minLen == 0) {
				return;
			} else {
				log.error("target = null and minLen != 0");
				throw new BusinessException(errorCode, minLen, maxLen);
			}
		}
		try {
			String targetStr = new String(target.getBytes("GBK"), "iso-8859-1");
			int len = targetStr.length();
			if (len < minLen || len > maxLen) {
				log.error("string's length = [{}], it < [{}] or it > [{}]", len, minLen, maxLen);
				throw new BusinessException(errorCode, minLen, maxLen);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}

	public static void validateScore(int target, int maxValue, String errorCode) {
		if (target < 0 || target > maxValue) {
			log.error("target = [{}], maxValue = [{}]", target, maxValue);
			throw new BusinessException(errorCode);
		}
	}
}
