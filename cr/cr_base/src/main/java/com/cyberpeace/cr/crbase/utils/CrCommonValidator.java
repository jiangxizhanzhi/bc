package com.cyberpeace.cr.crbase.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.EnumUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.constant.CrCommonConstants;
import com.google.common.collect.Sets;

import lombok.extern.slf4j.Slf4j;

/**
 * 靶场常用验证人
 *
 * @author SN
 * @date 2024/11/13
 */
@Slf4j
public class CrCommonValidator {

	/**
	 * 验证id是否为空
	 *
	 * @param id id
	 */
	public static void validateId(String id) {
		if (StringUtils.isBlank(id)) {
			log.error("id is blank");
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}
	}

	/**
	 * 验证用户id是否空
	 *
	 * @param userId 用户id
	 */
	public static void validateUserId(String userId) {
		if (StringUtils.isBlank(userId)) {
			log.error("userId is blank");
			throw new BusinessException(CrBusinessErrorCode.BLANK_USER_ID);
		}
	}

	/**
	 * 验证字符串长度。中文算2个字符。
	 *
	 * @param target    目标
	 * @param minLen    最小
	 * @param maxLen    最大 len
	 * @param errorCode 错误代号
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

	/**
	 * 验证列表如果空
	 *
	 * @param target    目标
	 * @param errorCode 错误代号
	 */
	public static <T> void validateListIfEmpty(List<T> target, String errorCode) {
		if (target.isEmpty()) {
			log.error("target is empty");
			throw new BusinessException(errorCode);
		}
	}

	/**
	 * 检查字符串列表重复
	 *
	 * @param target    目标
	 * @param errorCode 错误代号
	 */
	public static void checkStringListDuplicate(List<String> target, String errorCode) {
		Set<String> stringSet = Sets.newHashSet(target);
		if (target.size() != stringSet.size()) {
			log.error("Duplicate target! target = [{}]", StringUtils.join(target, ","));
			throw new BusinessException(errorCode);
		}
	}

	/**
	 * 检查字符串存在
	 *
	 * @param target    目标
	 * @param errorCode 错误代号
	 */
	public static void checkStringExist(String target, String errorCode) {
		if (target == null || target.isEmpty()) {
			log.error("target is null or target is empty");
			throw new BusinessException(errorCode);
		}
	}

	/**
	 * 验证得分
	 *
	 * @param target    目标
	 * @param maxValue  最大值
	 * @param errorCode 错误代号
	 */
	public static void validateScore(int target, int maxValue, String errorCode) {
		if (target < 0 || target > maxValue) {
			log.error("target = [{}], maxValue = [{}]", target, maxValue);
			throw new BusinessException(errorCode);
		}
	}

	/**
	 * 验证大小
	 *
	 * @param target    目标
	 * @param size      大小
	 * @param errorCode 错误代号
	 */
	public static <T> void validateSize(List<T> target, int size, String errorCode) {
		if (target.size() != size) {
			log.error("target = [{}], size = [{}], target.size != size", StringUtils.join(target, ","), size);
			throw new BusinessException(errorCode, size, target.size());
		}
	}

	/**
	 * 验证大小
	 *
	 * @param target    目标
	 * @param min       最小
	 * @param max       最大
	 * @param errorCode 错误代号
	 */
	public static <T> void validateSize(List<T> target, int min, int max, String errorCode) {
		if (target.size() < min || target.size() > max) {
			log.error("target = [{}], mix = [{}], max = [{}], target.size < min or target.size > max", StringUtils.join(target, ","), min, max);
			throw new BusinessException(errorCode);
		}
	}

	/**
	 * 验证范围
	 *
	 * @param target    目标
	 * @param minValue  最小值
	 * @param maxValue  最大值
	 * @param errorCode 错误代号
	 */
	public static void validateRange(int target, int minValue, int maxValue, String errorCode) {
		if (target < minValue || target > maxValue) {
			log.error("target = [{}], minValue = [{}], maxValue = [{}], target < minValue || target > maxValue", target, minValue, maxValue);
			throw new BusinessException(errorCode, minValue, maxValue);
		}
	}

	/**
	 * 默认 名称长度 1~255
	 *
	 * @param target    目标
	 * @param errorCode 错误代号
	 */
	public static void validateNameLen(String target, String errorCode) {
		validateLength(target, CrCommonConstants.MIN_STR_LEN, CrCommonConstants.MAX_NAME_STR_LEN,
				errorCode);
	}

	/**
	 * 默认描述长度 1~2048
	 *
	 * @param target    目标
	 * @param errorCode 错误代号
	 */
	public static void validateDescLen(String target, String errorCode) {
		validateLength(target, CrCommonConstants.MIN_STR_LEN, CrCommonConstants.MAX_DESC_LEN,
				errorCode);
	}

	/**
	 * 默认描述长度 1~65535
	 *
	 * @param target    目标
	 * @param errorCode 错误代号
	 */
	public static void validateTextLen(String target, String errorCode) {
		validateLength(target, CrCommonConstants.ZERO_STR_LEN, CrCommonConstants.MAX_TEXT_LEN,
				errorCode);
	}

	/**
	 * id、编号长度 1~32
	 *
	 * @param target
	 * @param errorCode 错误代号
	 */
	public static void validateIdLen(String target, String errorCode) {
		validateLength(target, CrCommonConstants.MIN_STR_LEN,
				CrCommonConstants.GENERATED_UUID_LENGTH, errorCode);
	}

	/**
	 * 校验字符串是否为ipv4地址
	 *
	 * @param ip ip地址
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
			log.error("Wrong ip format! ip = [{}]",ip);
			throw new BusinessException(CrBusinessErrorCode.WRONG_IP_FORMAT);
		}
	}

	/**
	 * 靶场常用验证人
	 */
	private CrCommonValidator() {}

	/**
	 * 验证枚举code
	 *
	 * @param code          代码
	 * @param authTypeClass 身份验证类型类
	 */
	public static <T extends GenericEnum> void validateEnumCode(Integer code,
			Class<T> authTypeClass) {
		if (null == code || EnumUtils.getEnumByCode(code, authTypeClass) == null) {
			throw new BusinessException(ErrorCode.INVALID_ENUM_CODE);
		}
	}

	/**
	 * 验证AHP矩阵数据次序
	 *
	 * @param matrixData 矩阵数据
	 * @return {@code List<String> }
	 */
	public static List<String> validateAhpMatrixDataOrder(String matrixData){
		JSONArray objects = JSON.parseArray(matrixData);
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
			Object detailObjs = JSON.parse(details);
			JSONObject parse = JsonUtil.toJsonObject(detailObjs.toString(), JSONObject.class);
			List<String> currentList = new ArrayList<>();
			for(String tmp:labels){
				String val = parse.getString(tmp);
				if(StringUtils.isEmpty(val) || StringUtils.isNumeric(val)){
					log.error("The matrix contains error data! matrix = [{}]", matrixData);
					throw new BusinessException(CrBusinessErrorCode.MATRIX_DATA_EMPTY_ERROR);
				}
				currentList.add(val);
			}
			checkData.addAll(currentList);
		}
		return checkData;
	}
}
