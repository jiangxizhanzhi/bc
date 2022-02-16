package com.cyberpeace.integratation.service;

import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;

public interface RemoteLogService {

	/**
	 * 新增操作日志
	 */

	void addCrLog(UserInfo userInfo, OperationModule operationModule, OperationType operationType,
			String operationTarget);

	void addOjLog(UserInfo userInfo, OperationModule operationModule, OperationType operationType,
			String operationTarget, String content);

}
