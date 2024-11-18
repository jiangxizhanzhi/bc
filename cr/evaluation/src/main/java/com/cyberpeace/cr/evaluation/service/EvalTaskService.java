package com.cyberpeace.cr.evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTemplate;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskOpsContext;

/**
 * 测评靶场-任务的处理
 *
 * @author SN
 * @version 1.0.0
 * @date 2024/11/18
 * @see IService
 * @since 1.0.0
 */
public interface EvalTaskService extends IService<EvalTask> {

	/**
	 * 获取测评靶场任务
	 *
	 * @param taskId
	 * @return
	 * @throws
	 * @see String
	 * @see EvalTask
	 */
	EvalTask getTask(String taskId);

	/**
	 * 获取活跃的测评靶场任务
	 *
	 * @param taskId
	 * @return
	 * @throws
	 * @see String
	 * @see EvalTask
	 */
	EvalTask getActiveTask(String taskId);

	/**
	 * 构造任务操作context
	 *
	 * @param taskId
	 * @param validateUser 是否校验用户身份
	 * @return
	 * @throws
	 * @see String
	 * @see Boolean
	 * @see EvalTaskOpsContext
	 */
	EvalTaskOpsContext getEvalTaskOpsContext(String taskId, Boolean validateUser);

	/**
	 * 启动测评靶场任务
	 *
	 * @param evalTemplate
	 * @return
	 * @throws
	 * @see EvalTemplate
	 * @see String
	 */
	String startTask(EvalTemplate evalTemplate);

	/**
	 * 停止测评靶场任务
	 *
	 * @param evalTaskOpsContext
	 * @return
	 * @throws
	 * @see EvalTaskOpsContext
	 * @see Boolean
	 */
	Boolean stopTask(EvalTaskOpsContext evalTaskOpsContext);

	/**
	 * 删除测评靶场任务
	 *
	 * @param evalTaskOpsContext
	 * @return
	 * @throws
	 * @see EvalTaskOpsContext
	 * @see Boolean
	 */
	Boolean deleteTask(EvalTaskOpsContext evalTaskOpsContext);

	/**
	 * 清除测评靶场任务场景相关信息
	 *
	 * @param evalTaskOpsContext
	 * @throws
	 * @see EvalTaskOpsContext
	 */
	void clearTaskScene(EvalTaskOpsContext evalTaskOpsContext);

	/**
	 * 清除测评靶场任务及其场景信息
	 *
	 * @param evalTaskOpsContext
	 * @throws
	 * @see EvalTaskOpsContext
	 */
	void clearTaskAndScene(EvalTaskOpsContext evalTaskOpsContext);

	/**
	 * 清除测评靶场当前任务信息
	 *
	 * @param templateId
	 * @param taskId
	 * @throws
	 * @see String
	 * @see String
	 */
	void clearCurrentTask(String templateId, String taskId);

	/**
	 * 校验测评靶场任务名称
	 *
	 * @param taskName
	 * @param templateId
	 * @param taskId
	 * @return
	 * @throws
	 * @see String
	 * @see String
	 * @see String
	 * @see Boolean
	 */
	Boolean validateName(String taskName, String templateId, String taskId);

}
