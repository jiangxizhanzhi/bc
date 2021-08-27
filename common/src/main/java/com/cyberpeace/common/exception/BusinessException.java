package com.cyberpeace.common.exception;

/**
 * 业务处理异常
 * @author yinqingrong
 */
public class BusinessException extends RuntimeException {

	public BusinessException(String message) {
		super(message);
	}
}
