package com.cyberpeace.cr.evaluation.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTaskStep;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskStepContext;
import com.cyberpeace.cr.evaluation.service.context.EvalTimeLineContext;

/**
 * 测试靶场-任务步骤的处理
 */
public interface EvalTaskStepService extends IService<EvalTaskStep> {

	Boolean startStep(String stepId);

	Boolean stopStep(String stepId);

	Boolean removeByTaskId(String taskId);

	List<EvalTaskStepContext> getTaskSteps(String taskId);

	List<EvalTimeLineContext> getFinishedSteps(String taskId);

}
