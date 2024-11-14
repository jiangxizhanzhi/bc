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
 * @date 2024/11/14
 */
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
