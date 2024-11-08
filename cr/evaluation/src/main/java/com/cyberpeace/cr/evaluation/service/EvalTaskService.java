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
