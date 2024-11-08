package com.cyberpeace.integratation.service;

import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;

/**
 * 远程日志Service
 *
 * @author SN
 * @date 2024/11/08
 */
public interface RemoteLogService {

	/**
	 * 新增操作日志
	 *
	 * @param userInfo        用户信息
	 * @param operationModule 实操模块
	 * @param operationType   实操类型
	 * @param operationTarget 实操目标
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
	 */
	void addOjLog(UserInfo userInfo, OperationModule operationModule, OperationType operationType,
			String operationTarget, String content);

}
