package com.cyberpeace.cr.evaluation.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTaskStep;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalTaskMapper;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalTaskStepMapper;
import com.cyberpeace.cr.evaluation.service.EvalTaskStepService;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskStepConfig;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskStepContext;
import com.cyberpeace.cr.evaluation.service.context.EvalTimeLineContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 评估任务步Service实现
 *
 * @author SN
 * @date 2024/11/08
 */
/**
 * 这段代码定义了一个名为 `EvalTaskStepServiceImpl` 的服务类，用于处理与评估任务步骤相关的业务逻辑。以下是代码的主要功能和结构分析： 
 * ### 类定义和注解 
 * - **包名**: `com.cyberpeace.cr.evaluation.service.impl` 
 * - **类名**: `EvalTaskStepServiceImpl` 
 * - **继承**: 继承自 `ServiceImpl<EvalTaskStepMapper, EvalTaskStep>`，这是一个基于 MyBatis-Plus 的基础实现类。 
 * - **注解**: 
 * - `@Slf4j`: 引入了 Lombok 的日志记录功能。 
 * - `@Service`: 表明这是一个 Spring 服务组件。 
 * ### 成员变量 
 * - **依赖注入**: 
 * - `EvalTaskStepMapper evalTaskStepMapper`: 用于访问评估任务步骤的数据表。 
 * - `EvalTaskMapper evalTaskMapper`: 用于访问评估任务的数据表。 
 * ### 方法 
 * 1. **getTaskStep(String stepId)** 
 * - 获取指定 ID 的评估任务步骤。 
 * - 如果步骤不存在，则抛出 `BusinessException`。 
 * 2. **startStep(EvalTaskStep evalTaskStep)** 
 * - 启动评估任务步骤。 
 * - 检查任务是否正在运行、步骤是否已经启动、以及前序步骤是否已完成。 
 * - 更新步骤的启动时间并返回 `true`。 
 * 3. **stopStep(EvalTaskStep evalTaskStep)** 
 * - 停止评估任务步骤。 
 * - 检查任务是否正在运行、步骤是否已经启动或结束。 
 * - 更新步骤的结束时间并返回 `true`。 
 * 4. **removeByTaskId(String taskId)** 
 * - 根据任务 ID 删除所有相关步骤。 
 * - 返回 `true`。 
 * 5. **getTaskSteps(String taskId)** 
 * - 获取指定任务的所有步骤。 
 * - 返回一个包含 `EvalTaskStepContext` 对象的列表。 
 * - 处理每个步骤的状态，并设置前序步骤的状态。 
 * 6. **getTemplateSteps(String stepConfig)** 
 * - 根据步骤配置获取模板步骤。 
 * - 将 JSON 配置转换为 `EvalTaskStepConfig` 对象，并进一步转换为 `EvalTaskStepContext` 对象。 
 * - 返回一个包含 `EvalTaskStepContext` 对象的列表。 
 * 7. **getFinishedSteps(String taskId)** 
 * - 获取指定任务中已完成的步骤。 
 * - 返回一个包含 `EvalTimeLineContext` 对象的列表。 
 * - 只包括有结束时间的步骤。 
 * ### 总结 
 * 这个服务类主要负责处理与评估任务步骤相关的 CRUD 操作，包括启动、停止、删除步骤，以及获取任务步骤和模板步骤的信息。它使用了 MyBatis-Plus 进行数据访问，并通过 Lombok 简化了日志记录和对象复制等操作。 
**/
@Service
@Slf4j
public class EvalTaskStepServiceImpl extends ServiceImpl<EvalTaskStepMapper, EvalTaskStep> implements EvalTaskStepService {

	/**
	 * 评估任务步Mapper
	 */
	@Autowired
	private EvalTaskStepMapper evalTaskStepMapper;

	/**
	 * 评估任务Mapper
	 */
	@Autowired
	private EvalTaskMapper evalTaskMapper;

	/**
	 * 获取任务步
	 *
	 * @param stepId 步id
	 * @return {@code EvalTaskStep }
	 */
	@Override
	public EvalTaskStep getTaskStep(String stepId) {

		EvalTaskStep evalTaskStep = getById(stepId);

		if (evalTaskStep == null) {
			log.error("The evaluation step does not exist! stepId = [{}]", stepId);
			throw new BusinessException(CrBusinessErrorCode.EVAL_STEP_NOT_EXIST);
		}

		return evalTaskStep;

	}

	/**
	 * 开始步骤
	 *
	 * @param evalTaskStep 评估任务步骤
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean startStep(EvalTaskStep evalTaskStep) {

		// 关闭的任务步骤不允许操作
		if (!evalTaskMapper.isNotFinished(evalTaskStep.getTaskId())) {
			log.error("The evaluation task is not running! TaskId = [{}]", evalTaskStep.getTaskId());
			throw new BusinessException(CrBusinessErrorCode.EVAL_TASK_NOT_RUNNING);
		}

		// 校验，已启动的步骤不允许重复启动
		if (evalTaskStep.getStartTime() != null) {
			log.error("Evaluation step is started! evalTaskStep = [{}]", JsonUtil.toJsonString(evalTaskStep));
			throw new BusinessException(CrBusinessErrorCode.EVAL_STEP_IS_STARTED);
		}

		// 校验，只有前序步骤完成才能启动此步骤
		Integer stage = evalTaskStep.getStage();
		String taskId = evalTaskStep.getTaskId();
		if (stage > 1 && evalTaskStepMapper.isNotFinished(taskId, (stage - 1))) {
			log.error("Preceding evaluation step is not finished! stage = [{}], taskId = [{}]", stage, taskId);
			throw new BusinessException(CrBusinessErrorCode.PRECEDING_STEP_NOT_FINISHED);
		}

		evalTaskStep.setStartTime(new Date());
		updateById(evalTaskStep);
		return true;
	}

	/**
	 * 停止步骤
	 *
	 * @param evalTaskStep 评估任务步骤
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean stopStep(EvalTaskStep evalTaskStep) {

		// 关闭的任务步骤不允许操作
		if (!evalTaskMapper.isNotFinished(evalTaskStep.getTaskId())) {
			log.error("The evaluation task is not running! TaskId = [{}]", evalTaskStep.getTaskId());
			throw new BusinessException(CrBusinessErrorCode.EVAL_TASK_NOT_RUNNING);
		}

		// 校验，未启动的步骤不允许结束
		if (evalTaskStep.getStartTime() == null) {
			log.error("Evaluation step not started! evalTaskStep = [{}]", JsonUtil.toJsonString(evalTaskStep));
			throw new BusinessException(CrBusinessErrorCode.EVAL_STEP_NOT_STARTED);
		}

		// 校验，已结束的步骤不允许结束
		if (evalTaskStep.getEndTime() != null) {
			log.error("Evaluation step is finished! evalTaskStep = [{}]", JsonUtil.toJsonString(evalTaskStep));
			throw new BusinessException(CrBusinessErrorCode.EVAL_STEP_IS_FINISHED);
		}

		evalTaskStep.setEndTime(new Date());
		updateById(evalTaskStep);
		return true;
	}

	/**
	 * 删除由任务id
	 *
	 * @param taskId 任务id
	 * @return {@code Boolean }
	 */
	@Override
	public Boolean removeByTaskId(String taskId) {
		List<String> stepList = evalTaskStepMapper.selectStepIdsByTaskId(taskId);
		removeByIds(stepList);
		return true;
	}

	/**
	 * 获取任务步骤
	 *
	 * @param taskId 任务id
	 * @return {@code List<EvalTaskStepContext> }
	 */
	@Override
	public List<EvalTaskStepContext> getTaskSteps(String taskId) {

		// 获取任务步骤列表
		EvalTask evalTask = evalTaskMapper.selectById(taskId);
		List<EvalTaskStepContext> evalTaskStepContexts = Lists.newArrayList();
		List<EvalTaskStep> evalTaskSteps = evalTaskStepMapper.selectStepsByTaskId(taskId);

		// 处理步骤信息
		for (int i = 0; i < evalTaskSteps.size(); i++) {

			EvalTaskStep evalTaskStep = evalTaskSteps.get(i);

			EvalTaskStepContext evalTaskStepContext = BeanUtils.copy(evalTaskStep,
					EvalTaskStepContext.class);

			// 设置步骤状态
			evalTaskStepContext.setStatus();

			// 设置前序步骤状态
			if (StringUtils.isNotEmpty(evalTask.getTaskName())) {
				evalTaskStepContext.setPreStepStatus(EvalTaskStepContext.Status.INIT.getCode());
				evalTaskStepContext.mergeStatus();
			} else if (i == 0) {
				evalTaskStepContext.setPreStepStatus(EvalTaskStepContext.Status.FINISHED.getCode());
			} else {
				evalTaskStepContext.setPreStepStatus(evalTaskStepContexts.get(i - 1).getStatus());
			}

			evalTaskStepContexts.add(evalTaskStepContext);
		}

		return evalTaskStepContexts;
	}

	/**
	 * 获取模板步骤
	 *
	 * @param stepConfig 步骤配置
	 * @return {@code List<EvalTaskStepContext> }
	 */
	@Override
	public List<EvalTaskStepContext> getTemplateSteps(String stepConfig) {

		List<EvalTaskStepContext> evalTaskStepContexts = Lists.newArrayList();

		List<EvalTaskStepConfig> evalTaskStepConfigList = JsonUtil.toJsonObjectArray(stepConfig,
				EvalTaskStepConfig.class);

		for (EvalTaskStepConfig evalTaskStepConfig : evalTaskStepConfigList) {
			EvalTaskStepContext evalTaskStepContext = BeanUtils.copy(evalTaskStepConfig,
					EvalTaskStepContext.class);
			evalTaskStepContexts.add(evalTaskStepContext);
		}

		return evalTaskStepContexts;
	}

	/**
	 * 获取完成步骤
	 *
	 * @param taskId 任务id
	 * @return {@code List<EvalTimeLineContext> }
	 */
	@Override
	public List<EvalTimeLineContext> getFinishedSteps(String taskId) {

		List<EvalTimeLineContext> evalTimeLineContextList = Lists.newArrayList();

		List<EvalTaskStep> evalTaskSteps = evalTaskStepMapper.selectStepsByTaskId(taskId);

		for (EvalTaskStep evalTaskStep : evalTaskSteps) {
			if (evalTaskStep.getEndTime() != null) {
				evalTimeLineContextList
						.add(BeanUtils.copy(evalTaskStep, EvalTimeLineContext.class));
			}
		}

		return evalTimeLineContextList;
	}

}
