package com.cyberpeace.oj.common.constant;

/**
 * 统一错误吗
 */
public interface ErrorCode {

	/**
	 * 参数校验异常
	 */
	String METHOD_ARG_NOT_VALID = "0001";

	/**
	 * 通过参数中的resource_id无法找到对应资源
	 * */
	String RESOURCE_NOT_FOUND = "0002";

	String INTERNAL_ERROR = "9999";
}
