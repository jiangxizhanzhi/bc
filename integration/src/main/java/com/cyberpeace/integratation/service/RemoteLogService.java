/**
 * 这段代码定义了一个名为 `RemoteLogService` 的接口，该接口用于远程记录日志。以下是对其主要特性的简要分析： 
 * ### 接口名称 
 * - `RemoteLogService`: 该接口主要用于远程记录操作日志。 
 * ### 方法 
 * #### 1. `addCrLog` 
 * - **功能**: 记录操作日志。 
 * - **参数**: 
 * - `UserInfo userInfo`: 用户信息。 
 * - `OperationModule operationModule`: 操作模块（例如：用户管理、订单处理等）。 
 * - `OperationType operationType`: 操作类型（例如：创建、更新、删除等）。 
 * - `String operationTarget`: 操作目标（例如：某个具体的资源或对象）。 
 * - **返回值**: 无（`void`），表示这是一个命令方法，只执行操作而不返回结果。 
 * #### 2. `addOjLog` 
 * - **功能**: 记录OJ（Online Judge）日志。 
 * - **参数**: 
 * - `UserInfo userInfo`: 用户信息。 
 * - `OperationModule operationModule`: 操作模块。 
 * - `OperationType operationType`: 操作类型。 
 * - `String operationTarget`: 操作目标。 
 * - `String content`: 日志内容（可能包含更详细的描述或其他信息）。 
 * - **返回值**: 无（`void`），同上，这是一个命令方法。 
 * ### 常量引用 
 * - `OperationModule` 和 `OperationType` 是从 `com.cyberpeace.integratation.constant` 包中导入的常量类。这些常量类可能定义了各种操作模块和操作类型的枚举值，以便在调用 `addCrLog` 和 `addOjLog` 方法时使用。 
 * ### 总结 
 * - `RemoteLogService` 接口提供了两个方法，分别用于记录不同类型的操作日志。 
 * - 这些方法需要传递用户信息、操作模块、操作类型以及操作目标，其中 `addOjLog` 方法还额外需要日志内容。 
 * - 通过这种方式，系统可以记录和跟踪用户在不同模块中的操作行为，有助于后续的日志分析和审计工作。 
**/
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
