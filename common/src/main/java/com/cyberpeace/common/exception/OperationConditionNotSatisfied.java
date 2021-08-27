package com.cyberpeace.common.exception;

/**
 * 操作前置条件不满足
 * */
public class OperationConditionNotSatisfied extends InvalidParamException {

	public OperationConditionNotSatisfied(String message) {
		super(message);
	}
}
