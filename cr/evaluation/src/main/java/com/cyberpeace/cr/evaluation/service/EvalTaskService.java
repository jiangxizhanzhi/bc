package com.cyberpeace.cr.evaluation.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTemplate;

/**
 * 测试靶场-任务的处理
 */
public interface EvalTaskService extends IService<EvalTask> {

	Boolean getStepsByTaskId(String taskId);

	EvalTask getTask(String taskId);

	Boolean startTask(EvalTemplate evalTemplate);

	Boolean stopTask(EvalTask evalTask);

	Boolean deleteTask(EvalTask evalTask);

}
