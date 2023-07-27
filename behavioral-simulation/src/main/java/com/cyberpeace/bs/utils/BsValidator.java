package com.cyberpeace.bs.utils;

import org.apache.commons.lang3.StringUtils;

import com.cyberpeace.bs.constant.BsErrorCode;
import com.cyberpeace.common.exception.BusinessException;

/**
 * 验证器
 *
 * @author SN
 * @date 2023/07/27
 */
public class BsValidator {

	/**
	 * 验证id
	 *
	 * @param id id
	 */
	public static void validateId(String id) {
		if (StringUtils.isBlank(id)) {
			throw new BusinessException(BsErrorCode.RESOURCE_ID_MISSING);
		}
	}
}
