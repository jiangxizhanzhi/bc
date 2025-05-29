package com.cyberpeace.cr.evaluation.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTaskStep;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskStepContext;
import com.cyberpeace.cr.evaluation.service.context.EvalTimeLineContext;

/**
 * 测评靶场-任务步骤的处理
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalTaskStepService` 的接口，该接口继承自 `IService<EvalTaskStep>`。这个接口主要用于处理与测评靶场中的任务步骤相关的业务逻辑。下面是对代码的简要分析： 
 * ### 包声明 
 * ```java 
 * package com.cyberpeace.cr.evaluation.service; 
 * ``` 
 * 表明这个接口位于 `com.cyberpeace.cr.evaluation.service` 包中。 
 * ### 导入的类和接口 
 * ```java 
 * import java.util.List; 
 * import com.baomidou.mybatisplus.extension.service.IService; 
 * import com.cyberpeace.cr.evaluation.dao.entity.EvalTaskStep; 
 * import com.cyberpeace.cr.evaluation.service.context.EvalTaskStepContext; 
 * import com.cyberpeace.cr.evaluation.service.context.EvalTimeLineContext; 
 * ``` 
 * 导入了必要的类和接口，包括 `List`、`IService`、`EvalTaskStep`、`EvalTaskStepContext` 和 `EvalTimeLineContext`。 
 * ### 接口声明 
 * ```java 
 * public interface EvalTaskStepService extends IService<EvalTaskStep> { 
 * ``` 
 * `EvalTaskStepService` 继承自 `IService<EvalTaskStep>`，这意味着它扩展了 MyBatis-Plus 提供的基本 CRUD 操作。 
 * ### 方法声明 
 * 接口中定义了多个方法来处理与任务步骤相关的操作： 
 * 1. **获取步骤** 
 * ```java 
 * EvalTaskStep getTaskStep(String stepId); 
 * ``` 
 * 根据步骤 ID 获取一个步骤对象。 
 * 2. **启动步骤** 
 * ```java 
 * Boolean startStep(EvalTaskStep evalTaskStep); 
 * ``` 
 * 启动给定的任务步骤，并返回操作是否成功。 
 * 3. **完成步骤** 
 * ```java 
 * Boolean stopStep(EvalTaskStep evalTaskStep); 
 * ``` 
 * 完成给定的任务步骤，并返回操作是否成功。 
 * 4. **删除指定任务下所有步骤** 
 * ```java 
 * Boolean removeByTaskId(String taskId); 
 * ``` 
 * 根据任务 ID 删除所有相关步骤，并返回操作是否成功。 
 * 5. **获取任务下所有步骤** 
 * ```java 
 * List<EvalTaskStepContext> getTaskSteps(String taskId); 
 * ``` 
 * 根据任务 ID 获取所有步骤，并以 `EvalTaskStepContext` 对象列表的形式返回。 
 * 6. **获取模板下所有步骤** 
 * ```java 
 * List<EvalTaskStepContext> getTemplateSteps(String stepConfig); 
 * ``` 
 * 根据步骤配置获取所有步骤，并以 `EvalTaskStepContext` 对象列表的形式返回。 
 * 7. **获取任务下所有已完成的步骤** 
 * ```java 
 * List<EvalTimeLineContext> getFinishedSteps(String taskId); 
 * ``` 
 * 根据任务 ID 获取所有已完成的步骤，并以 `EvalTimeLineContext` 对象列表的形式返回。 
 * ### 总结 
 * `EvalTaskStepService` 接口提供了一组方法来管理与测评靶场任务步骤相关的各种操作，包括获取、启动、停止、删除和查询步骤等。这些方法的设计使得调用者可以方便地进行任务步骤的管理和控制。 
**/
public interface EvalTaskStepService extends IService<EvalTaskStep> {

	/**
	 * 获取步骤
	 *
	 * @param stepId
	 * @return
	 */
	EvalTaskStep getTaskStep(String stepId);

	/**
	 * 启动步骤
	 *
	 * @param evalTaskStep
	 * @return
	 */
	Boolean startStep(EvalTaskStep evalTaskStep);

	/**
	 * 完成步骤
	 *
	 * @param evalTaskStep
	 * @return
	 */
	Boolean stopStep(EvalTaskStep evalTaskStep);

	/**
	 * 删除指定任务下所有步骤
	 *
	 * @param taskId
	 * @return
	 */
	Boolean removeByTaskId(String taskId);

	/**
	 * 获取任务下所有步骤
	 *
	 * @param taskId
	 * @return
	 */
	List<EvalTaskStepContext> getTaskSteps(String taskId);

	/**
	 * 获取模板下所有步骤
	 *
	 * @param stepConfig
	 * @return
	 */
	List<EvalTaskStepContext> getTemplateSteps(String stepConfig);

	/**
	 * 获取任务下所有已完成的步骤
	 *
	 * @param taskId
	 * @return
	 */
	List<EvalTimeLineContext> getFinishedSteps(String taskId);

}

