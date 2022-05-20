package com.cyberpeace.cr.evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTemplate;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskOpsContext;

/**
 * 测评靶场-任务的处理
 */
public interface EvalTaskService extends IService<EvalTask> {

	/** 获取测评靶场任务
	 * @param taskId
	 * @return
	 */
	EvalTask getTask(String taskId);

	/** 获取活跃的测评靶场任务
	 * @param taskId
	 * @return
	 */
	EvalTask getActiveTask(String taskId);

	EvalTaskOpsContext getEvalTaskOpsContext(String taskId, Boolean validateUser);

	/** 启动测评靶场任务
	 * @param evalTemplate
	 * @return
	 */
	String startTask(EvalTemplate evalTemplate);

	/** 停止测评靶场任务
	 * @param evalTaskOpsContext
	 * @return
	 */
	Boolean stopTask(EvalTaskOpsContext evalTaskOpsContext);

	/** 删除测评靶场任务
	 * @param evalTaskOpsContext
	 * @return
	 */
	Boolean deleteTask(EvalTaskOpsContext evalTaskOpsContext);

	/** 清除测评靶场任务场景相关信息
	 * @param evalTaskOpsContext
	 * @return
	 */
	void clearTaskScene(EvalTaskOpsContext evalTaskOpsContext);

	/** 校验测评靶场任务名称
	 * @param taskName
	 * @param templateId
	 * @param taskId
	 * @return
	 */
	Boolean validateName(String taskName, String templateId, String taskId);

	/** 清除测评靶场当前任务信息
	 * @param templateId
	 * @return
	 */
	void clearCurrentTask(String templateId);

	void clearTaskAndScene(EvalTaskOpsContext evalTaskOpsContext);

}
