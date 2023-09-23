package com.cyberpeace.cr.evaluation.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

@Service
@Slf4j
public class EvalTaskStepServiceImpl extends ServiceImpl<EvalTaskStepMapper, EvalTaskStep> implements EvalTaskStepService {

	@Autowired
	private EvalTaskStepMapper evalTaskStepMapper;

	@Autowired
	private EvalTaskMapper evalTaskMapper;

	@Override
	public EvalTaskStep getTaskStep(String stepId) {

		EvalTaskStep evalTaskStep = getById(stepId);

		if (evalTaskStep == null) {
			log.error("The evaluation step does not exist! stepId = [{}]", stepId);
			throw new BusinessException(CrBusinessErrorCode.EVAL_STEP_NOT_EXIST);
		}

		return evalTaskStep;

	}

	@Override
	public Boolean startStep(EvalTaskStep evalTaskStep) {

		// 关闭的任务步骤不允许操作
		if (Boolean.FALSE.equals(evalTaskMapper.isNotFinished(evalTaskStep.getTaskId()))) {
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
		if (stage > 1
				&& Boolean.TRUE.equals(evalTaskStepMapper.isNotFinished(taskId, (stage - 1)))) {
			log.error("Preceding evaluation step is not finished! stage = [{}], taskId = [{}]", stage, taskId);
			throw new BusinessException(CrBusinessErrorCode.PRECEDING_STEP_NOT_FINISHED);
		}

		evalTaskStep.setStartTime(new Date());
		updateById(evalTaskStep);
		return true;
	}

	@Override
	public Boolean stopStep(EvalTaskStep evalTaskStep) {

		// 关闭的任务步骤不允许操作
		if (Boolean.FALSE.equals(evalTaskMapper.isNotFinished(evalTaskStep.getTaskId()))) {
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

	@Override
	public Boolean removeByTaskId(String taskId) {
		List<String> stepList = evalTaskStepMapper.selectStepIdsByTaskId(taskId);
		removeByIds(stepList);
		return true;
	}

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