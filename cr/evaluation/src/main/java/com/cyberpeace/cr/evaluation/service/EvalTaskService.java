/**
 * 这段代码定义了一个名为 `EvalTaskService` 的接口，该接口扩展了 `IService<EvalTask>` 接口。`EvalTaskService` 主要用于处理与测评靶场任务相关的各种操作。以下是详细的分析： 
 * ### 包和导入 
 * - **包声明**：`com.cyberpeace.cr.evaluation.service` 
 * - **导入的类和接口**： 
 * - `com.baomidou.mybatisplus.extension.service.IService` 
 * - `com.cyberpeace.cr.evaluation.dao.entity.EvalTask` 
 * - `com.cyberpeace.cr.evaluation.dao.entity.EvalTemplate` 
 * - `com.cyberpeace.cr.evaluation.service.context.EvalTaskOpsContext` 
 * ### 接口描述 
 * - **作者**：SN 
 * - **日期**：2024年11月8日 
 * - **功能**：测评靶场任务的处理 
 * ### 方法说明 
 * 1. **获取测评靶场任务** 
 * ```java 
 * EvalTask getTask(String taskId); 
 * ``` 
 * - 参数：`taskId` (任务ID) 
 * - 返回值：`EvalTask` 对象 
 * 2. **获取活跃的测评靶场任务** 
 * ```java 
 * EvalTask getActiveTask(String taskId); 
 * ``` 
 * - 参数：`taskId` (任务ID) 
 * - 返回值：`EvalTask` 对象 
 * 3. **构造任务操作context** 
 * ```java 
 * EvalTaskOpsContext getEvalTaskOpsContext(String taskId, Boolean validateUser); 
 * ``` 
 * - 参数：`taskId` (任务ID)，`validateUser` (是否校验用户身份) 
 * - 返回值：`EvalTaskOpsContext` 对象 
 * 4. **启动测评靶场任务** 
 * ```java 
 * String startTask(EvalTemplate evalTemplate); 
 * ``` 
 * - 参数：`evalTemplate` (测评模板) 
 * - 返回值：`String` (可能表示任务ID或其他标识) 
 * 5. **停止测评靶场任务** 
 * ```java 
 * Boolean stopTask(EvalTaskOpsContext evalTaskOpsContext); 
 * ``` 
 * - 参数：`evalTaskOpsContext` (任务操作上下文) 
 * - 返回值：`Boolean` (成功或失败) 
 * 6. **删除测评靶场任务** 
 * ```java 
 * Boolean deleteTask(EvalTaskOpsContext evalTaskOpsContext); 
 * ``` 
 * - 参数：`evalTaskOpsContext` (任务操作上下文) 
 * - 返回值：`Boolean` (成功或失败) 
 * 7. **清除测评靶场任务场景相关信息** 
 * ```java 
 * void clearTaskScene(EvalTaskOpsContext evalTaskOpsContext); 
 * ``` 
 * - 参数：`evalTaskOpsContext` (任务操作上下文) 
 * 8. **清除测评靶场任务及其场景信息** 
 * ```java 
 * void clearTaskAndScene(EvalTaskOpsContext evalTaskOpsContext); 
 * ``` 
 * - 参数：`evalTaskOpsContext` (任务操作上下文) 
 * 9. **清除测评靶场当前任务信息** 
 * ```java 
 * void clearCurrentTask(String templateId, String taskId); 
 * ``` 
 * - 参数：`templateId` (模板ID)，`taskId` (任务ID) 
 * 10. **校验测评靶场任务名称** 
 * ```java 
 * Boolean validateName(String taskName, String templateId, String taskId); 
 * ``` 
 * - 参数：`taskName` (任务名称)，`templateId` (模板ID)，`taskId` (任务ID) 
 * - 返回值：`Boolean` (验证结果) 
 * ### 总结 
 * - `EvalTaskService` 接口定义了一系列与测评靶场任务相关的操作。 
 * - 这些方法涵盖了任务的获取、启动、停止、删除以及任务场景信息的管理等。 
 * - 通过这些方法，可以实现对测评靶场任务的全面管理和控制。 
**/
package com.cyberpeace.cr.evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTemplate;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskOpsContext;

/**
 * 测评靶场-任务的处理
 *
 * @author SN
 * @date 2024/11/08
 */
public interface EvalTaskService extends IService<EvalTask> {

	/**
	 * 获取测评靶场任务
	 *
	 * @param taskId
	 * @return
	 */
	EvalTask getTask(String taskId);

	/**
	 * 获取活跃的测评靶场任务
	 *
	 * @param taskId
	 * @return
	 */
	EvalTask getActiveTask(String taskId);

	/**
	 * 构造任务操作context
	 *
	 * @param taskId
	 * @param validateUser 是否校验用户身份
	 * @return
	 */
	EvalTaskOpsContext getEvalTaskOpsContext(String taskId, Boolean validateUser);

	/**
	 * 启动测评靶场任务
	 *
	 * @param evalTemplate
	 * @return
	 */
	String startTask(EvalTemplate evalTemplate);

	/**
	 * 停止测评靶场任务
	 *
	 * @param evalTaskOpsContext
	 * @return
	 */
	Boolean stopTask(EvalTaskOpsContext evalTaskOpsContext);

	/**
	 * 删除测评靶场任务
	 *
	 * @param evalTaskOpsContext
	 * @return
	 */
	Boolean deleteTask(EvalTaskOpsContext evalTaskOpsContext);

	/**
	 * 清除测评靶场任务场景相关信息
	 *
	 * @param evalTaskOpsContext
	 */
	void clearTaskScene(EvalTaskOpsContext evalTaskOpsContext);

	/**
	 * 清除测评靶场任务及其场景信息
	 *
	 * @param evalTaskOpsContext
	 */
	void clearTaskAndScene(EvalTaskOpsContext evalTaskOpsContext);

	/**
	 * 清除测评靶场当前任务信息
	 *
	 * @param templateId
	 * @param taskId
	 */
	void clearCurrentTask(String templateId, String taskId);

	/**
	 * 校验测评靶场任务名称
	 *
	 * @param taskName
	 * @param templateId
	 * @param taskId
	 * @return
	 */
	Boolean validateName(String taskName, String templateId, String taskId);

}
