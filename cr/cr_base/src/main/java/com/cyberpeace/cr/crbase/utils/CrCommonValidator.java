package com.cyberpeace.cr.crbase.utils;

import java.io.UnsupportedEncodingException;
import java.util.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;

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

	public static void validateRange(int target, int minValue, int maxValue, String errorCode) {
		if (target < minValue || target > maxValue) {
			throw new BusinessException(errorCode, minValue, maxValue);
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

	/**
	 * 默认描述长度 1~65535
	 */
	public static void validateTextLen(String target, String errorCode) {
		validateLength(target, CrCommonConstants.ZERO_STR_LEN, CrCommonConstants.MAX_TEXT_LEN,
				errorCode);
	}

	/**
	 * id、编号长度 1~32
	 * @param target
	 * @param errorCode
	 */
	public static void validateIdLen(String target, String errorCode) {
		validateLength(target, CrCommonConstants.MIN_STR_LEN,
				CrCommonConstants.GENERATED_UUID_LENGTH, errorCode);
	}

	/**
	 * 校验字符串是否为ipv4地址
	 */
	public static void validateIp(String ip) {

		if (ip == null) {
			return;
		}

		String regex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
				+ "(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				+ "(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
				+ "(00?\\d|1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";

		if (!ip.matches(regex)) {
			throw new BusinessException(CrBusinessErrorCode.WRONG_IP_FORMAT);
		}
	}

	public static List<String> validateAhpMatrixDataOrder(String matrixData){
		JSONArray objects = JSONArray.parseArray(matrixData);
		List<String> labels = new ArrayList<>();
		for (int j = 0; j < objects.size(); j++) {
			JSONObject temp = objects.getJSONObject(j);
			String label = temp.getString("label");
			if(!labels.contains(label)){
				labels.add(label);
			}
		}
		List<String> checkData = new ArrayList<>();
		for (int j = 0; j < objects.size(); j++) {
			JSONObject temp = objects.getJSONObject(j);
			String details = temp.getString("data");
			Object detailObjs = JSONObject.parse(details);
			JSONObject parse = JsonUtil.toJsonObject(detailObjs.toString(), JSONObject.class);
			List<String> currentList = new ArrayList<>();
			for(String tmp:labels){
				String val = parse.getString(tmp);
				if(StringUtils.isEmpty(val) || StringUtils.isNumeric(val)){
					throw new BusinessException(CrBusinessErrorCode.MATRIX_DATA_EMPTY_ERROR);
				}
				currentList.add(val);
			}
			checkData.addAll(currentList);
		}
		return checkData;
	}

}
