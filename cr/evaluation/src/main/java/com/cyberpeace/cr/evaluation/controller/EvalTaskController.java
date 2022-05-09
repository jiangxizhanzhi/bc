package com.cyberpeace.cr.evaluation.controller;

import java.util.Date;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cr.evaluation.controller.req.EvalTaskDeleteReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalTaskStartReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalTaskStepReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalTaskStopReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTaskRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTaskTimeLineRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTemplate;
import com.cyberpeace.cr.evaluation.service.EvalTaskService;
import com.cyberpeace.cr.evaluation.service.EvalTaskStepService;
import com.cyberpeace.cr.evaluation.service.EvalTemplateService;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskStepContext;
import com.cyberpeace.cr.evaluation.service.context.EvalTimeLineContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "测试靶场-任务")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/eval/task")
public class EvalTaskController {

	@Autowired
	private EvalTemplateService evalTemplateService;

	@Autowired
	private EvalTaskService evalTaskService;

	@Autowired
	private EvalTaskStepService evalTaskStepService;

	@ApiOperation("启动步骤")
	@PostMapping("/step/start")
	public BaseRsp<Boolean> startStep(@RequestBody EvalTaskStepReq evalTaskStepReq) {
		Boolean result = evalTaskStepService.startStep(evalTaskStepReq.getStepId());
		return new BaseRsp<>(result);
	}

	@ApiOperation("完成步骤")
	@PostMapping("/step/finish")
	public BaseRsp<Boolean> finishStep(@RequestBody EvalTaskStepReq evalTaskStepReq) {
		Boolean result = evalTaskStepService.stopStep(evalTaskStepReq.getStepId());
		return new BaseRsp<>(result);

	}

	@ApiOperation("启动测试任务")
	@PostMapping("/start")
	public BaseRsp<Boolean> startEvalTask(@RequestBody EvalTaskStartReq evalTaskStartReq) {

		String templateId = evalTaskStartReq.getTemplateId();

		EvalTemplate evalTemplate = evalTemplateService.getById(templateId);

		evalTaskService.startTask(evalTemplate);

		return new BaseRsp<>(true);
	}

	@ApiOperation("关闭测试任务")
	@PostMapping("/stop")
	public BaseRsp<Boolean> stopEvalTask(@RequestBody EvalTaskStopReq evalTaskStopReq) {

		if (evalTaskStopReq.getTaskName() == null) {
			// 记录日志 todo
			evalTaskService.deleteTask(evalTaskStopReq.getTaskId());
		} else {
			// 记录日志
			evalTaskService.stopTask(evalTaskStopReq.getTaskId());

			// 是否需要保留localscene，是否需要mpSceneId todo
		}

		// 去除
		EvalTemplate evalTemplate = evalTemplateService.getById(evalTaskStopReq.getTemplateId());

		evalTemplate.setCurrentTaskId(null);

		return new BaseRsp<>(true);
	}

	@ApiOperation("查询测试任务")
	@GetMapping("")
	public BaseRsp<EvalTaskRsp> getEvalTask(
			@ApiParam(name = "template_id", value = "测试靶场模板id",
					required = true) @RequestParam("template_id") String templateId,
			@ApiParam(name = "task_id", value = "测试任务id",
					required = false) @RequestParam("task_id") String taskId) {

		// task_id未赋值时。默认返回当前实例
		return new BaseRsp<>(new EvalTaskRsp());
	}

	@ApiOperation("删除测试任务")
	@DeleteMapping("")
	public BaseRsp<Boolean> deleteEvalTask(@RequestBody EvalTaskDeleteReq evalTaskDeleteReq) {

		for (String taskId : evalTaskDeleteReq.getTaskIds()) {
			evalTaskService.deleteTask(taskId);
		}

		return new BaseRsp<>(true);
	}

	@ApiOperation("查询测试任务步骤")
	@GetMapping("/step/list")
	public BaseRsp<List<EvalTaskStepContext>> getStepList(
			@ApiParam(name = "template_id", value = "测试靶场模板id",
					required = true) @RequestParam("template_id") String templateId,
			@ApiParam(name = "task_id", value = "测试任务id",
					required = false) @RequestParam("task_id") String taskId) {

		if (taskId == null) {
			taskId = evalTemplateService.getById(templateId).getCurrentTaskId();
		}

		List<EvalTaskStepContext> evalTaskStepContextList = evalTaskStepService
				.getTaskSteps(taskId);

		return new BaseRsp<>(evalTaskStepContextList);
	}

	@ApiOperation("查询测试任务时间线")
	@GetMapping("/time_line")
	public BaseRsp<EvalTaskTimeLineRsp> getTimeLine(
			@ApiParam(name = "template_id", value = "测试靶场模板id",
					required = true) @RequestParam("template_id") String templateId,
			@ApiParam(name = "task_id", value = "测试任务id",
					required = false) @RequestParam("task_id") String taskId) {

		if (taskId == null) {
			taskId = evalTemplateService.getById(templateId).getCurrentTaskId();
		}

		EvalTask evalTask = evalTaskService.getById(taskId);
		List<EvalTimeLineContext> evalTimeLineContextList = evalTaskStepService
				.getFinishedSteps(taskId);

		EvalTaskTimeLineRsp evalTaskTimeLineRsp = new EvalTaskTimeLineRsp();
		evalTaskTimeLineRsp.setData(evalTimeLineContextList);
		evalTaskTimeLineRsp.setTaskId(taskId);
		evalTaskTimeLineRsp.setCurrentTime(new Date());
		evalTaskTimeLineRsp.setStartTime(evalTask.getStartTime());
		evalTaskTimeLineRsp.setEndTime(evalTask.getEndTime());

		return new BaseRsp<>(evalTaskTimeLineRsp);
	}

}