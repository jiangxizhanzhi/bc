package com.cyberpeace.rtks.util;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.ConfigUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.UnsupportedEncodingException;

@Slf4j
public class RtksCommonValidator {

	/**
	 * id、编号长度 1~32
	 * @param target
	 * @param errorCode
	 */
	public static void validateIdLen(String target, String errorCode) {
		validateLength(target, Integer.parseInt(ConfigUtil.getContextProperty("rtks.str.len.min")),
				32, errorCode);
	}

	public static void validateRange(int target, int minValue, int maxValue, String errorCode) {
		if (target < minValue || target > maxValue) {
			log.error("target = [{}], minValue = [{}], maxValue = [{}], target < minValue || target > maxValue", target, minValue, maxValue);
			throw new BusinessException(errorCode, minValue, maxValue);
		}
	}

	/**
	 * 默认描述长度 1~2048
	 */
	public static void validateDescLen(String target, String errorCode) {
		validateLength(target, Integer.parseInt(ConfigUtil.getContextProperty("rtks.str.len.min")),
				Integer.parseInt(ConfigUtil.getContextProperty("rtks.desc.len.max")), errorCode);
	}

	/**
	 * 默认 名称长度 1~255
	 */
	public static void validateNameLen(String target, String errorCode) {
		validateLength(target, Integer.parseInt(ConfigUtil.getContextProperty("rtks.str.len.min")),
				Integer.parseInt(ConfigUtil.getContextProperty("rtks.str.len.name.max")), errorCode);
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
}
