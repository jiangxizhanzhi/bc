package com.cyberpeace.integratation.service;

import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;

/**
 * 远程日志Service
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/14
 * @since 1.0.0
 */
public interface RemoteLogService {

	/**
	 * 新增操作日志
	 *
	 * @param userInfo        用户信息
	 * @param operationModule 实操模块
	 * @param operationType   实操类型
	 * @param operationTarget 实操目标
	 * @throws
	 * @see UserInfo
	 * @see OperationModule
	 * @see OperationType
	 * @see String
	 */

	void addCrLog(UserInfo userInfo, OperationModule operationModule, OperationType operationType,
			String operationTarget);

	/**
	 * 新增OJ日志
	 *
	 * @param userInfo        用户信息
	 * @param operationModule 实操模块
	 * @param operationType   实操类型
	 * @param operationTarget 实操目标
	 * @param content         内容
	 * @throws
	 * @see UserInfo
	 * @see OperationModule
	 * @see OperationType
	 * @see String
	 * @see String
	 */
	void addOjLog(UserInfo userInfo, OperationModule operationModule, OperationType operationType,
			String operationTarget, String content);

}
