package com.cyberpeace.common.util;

import com.cyberpeace.common.baseinterface.GenericEnum;
import com.cyberpeace.common.constant.CommonConstant;
import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.exception.BusinessException;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Set;

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
            throw new BusinessException(ErrorCode.RESOURCE_ID_MISSING);
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
            throw new BusinessException(ErrorCode.BLANK_USER_ID);
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
            String targetStr = new String(target.getBytes("GBK"), StandardCharsets.ISO_8859_1);
            int len = targetStr.length();
            if (len < minLen || len > maxLen) {
                log.error("string's length = [{}], it < [{}] or it > [{}]", len, minLen, maxLen);
                throw new BusinessException(errorCode, minLen, maxLen);
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public static <T> void validateListIfEmpty(List<T> target, String errorCode) {
        if (target.isEmpty()) {
            log.error("target is empty");
            throw new BusinessException(errorCode);
        }
    }

    public static void checkStringListDuplicate(List<String> target, String errorCode) {
        Set<String> stringSet = Sets.newHashSet(target);
        if (target.size() != stringSet.size()) {
            log.error("Duplicate target! target = [{}]", StringUtils.join(target, ","));
            throw new BusinessException(errorCode);
        }
    }

    public static void checkStringExist(String target, String errorCode) {
        if (target == null || target.isEmpty()) {
            log.error("target is null or target is empty");
            throw new BusinessException(errorCode);
        }
    }

    public static void validateScore(int target, int maxValue, String errorCode) {
        if (target < 0 || target > maxValue) {
            log.error("target = [{}], maxValue = [{}]", target, maxValue);
            throw new BusinessException(errorCode);
        }
    }

    public static <T> void validateSize(List<T> target, int size, String errorCode) {
        if (target.size() != size) {
            log.error("target = [{}], size = [{}], target.size != size", StringUtils.join(target, ","), size);
            throw new BusinessException(errorCode, size, target.size());
        }
    }

    public static <T> void validateSize(List<T> target, int min, int max, String errorCode) {
        if (target.size() < min || target.size() > max) {
            log.error("target = [{}], mix = [{}], max = [{}], target.size < min or target.size > max", StringUtils.join(target, ","), min, max);
            throw new BusinessException(errorCode);
        }
    }

    public static void validateRange(int target, int minValue, int maxValue, String errorCode) {
        if (target < minValue || target > maxValue) {
            log.error("target = [{}], minValue = [{}], maxValue = [{}], target < minValue || target > maxValue", target, minValue, maxValue);
            throw new BusinessException(errorCode, minValue, maxValue);
        }
    }

    /**
     * 默认 名称长度 1~255
     */
    public static void validateNameLen(String target, String errorCode) {
        validateLength(target, CommonConstant.MIN_STR_LEN, CommonConstant.MAX_NAME_STR_LEN,
                errorCode);
    }

    /**
     * 默认描述长度 1~2048
     */
    public static void validateDescLen(String target, String errorCode) {
        validateLength(target, CommonConstant.MIN_STR_LEN, CommonConstant.MAX_DESC_LEN,
                errorCode);
    }

    /**
     * 默认描述长度 1~65535
     */
    public static void validateTextLen(String target, String errorCode) {
        validateLength(target, CommonConstant.ZERO_STR_LEN, CommonConstant.MAX_TEXT_LEN,
                errorCode);
    }

    /**
     * id、编号长度 1~32
     *
     * @param target
     * @param errorCode
     */
    public static void validateIdLen(String target, String errorCode) {
        validateLength(target, CommonConstant.MIN_STR_LEN,
                CommonConstant.GENERATED_UUID_LENGTH, errorCode);
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
            log.error("Wrong ip format! ip = [{}]", ip);
            throw new BusinessException(ErrorCode.WRONG_IP_FORMAT);
        }
    }

    private CrCommonValidator() {
    }

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

}
