package com.cyberpeace.cr.evaluation.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.compress.utils.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTaskStep;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalTaskStepMapper;
import com.cyberpeace.cr.evaluation.service.EvalTaskStepService;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskStepContext;
import com.cyberpeace.cr.evaluation.service.context.EvalTimeLineContext;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class EvalTaskStepServiceImpl extends ServiceImpl<EvalTaskStepMapper, EvalTaskStep> implements EvalTaskStepService {

	@Autowired
	private EvalTaskStepMapper evalTaskStepMapper;

	@Override
	public Boolean startStep(String stepId) {
		EvalTaskStep evalTaskStep = getById(stepId);
		evalTaskStep.setStartTime(new Date());
		save(evalTaskStep);
		return true;
	}

	@Override
	public Boolean stopStep(String stepId) {
		EvalTaskStep evalTaskStep = getById(stepId);
		evalTaskStep.setEndTime(new Date());
		save(evalTaskStep);
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
		List<EvalTaskStepContext> evalTaskStepContexts = Lists.newArrayList();

		List<EvalTaskStep> evalTaskSteps = evalTaskStepMapper.selectStepsByTaskId(taskId);

		for (EvalTaskStep evalTaskStep : evalTaskSteps) {
			EvalTaskStepContext evalTaskStepContext = BeanUtils.copy(evalTaskStep,
					EvalTaskStepContext.class);
			evalTaskStepContext.setStepId(evalTaskStep.getResourceId());
			evalTaskStepContext.setStatus();
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
				EvalTimeLineContext evalTimeLineContext = BeanUtils.copy(evalTaskStep,
						EvalTimeLineContext.class);
				evalTimeLineContext.setStepId(evalTaskStep.getResourceId());
				evalTimeLineContextList.add(evalTimeLineContext);
			}
		}

		return evalTimeLineContextList;
	}

}