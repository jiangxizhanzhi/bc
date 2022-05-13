package com.cyberpeace.cr.evaluation.controller;

import java.util.Date;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.service.context.SceneViewContext;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cr.evaluation.controller.req.EvalTaskDeleteReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalTaskStartReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalTaskStepReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalTaskStopReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTaskRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTaskTimeLineRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTaskStep;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTemplate;
import com.cyberpeace.cr.evaluation.dao.mapper.EvalTaskMapper;
import com.cyberpeace.cr.evaluation.service.EvalSceneService;
import com.cyberpeace.cr.evaluation.service.EvalTaskService;
import com.cyberpeace.cr.evaluation.service.EvalTaskStepService;
import com.cyberpeace.cr.evaluation.service.EvalTemplateService;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskStepContext;
import com.cyberpeace.cr.evaluation.service.context.EvalTimeLineContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "靶场测评-任务")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/eval/task")
public class EvalTaskController {

	@Autowired
	private EvalTemplateService evalTemplateService;

	@Autowired
	private EvalTaskService evalTaskService;

	@Autowired
	private EvalSceneService evalSceneService;

	@Autowired
	private EvalTaskStepService evalTaskStepService;

	@Autowired
	private EvalTaskMapper evalTaskMapper;

	@ApiOperation("启动步骤")
	@PostMapping("/step/start")
	public BaseRsp<Boolean> startStep(@RequestBody @Validated EvalTaskStepReq evalTaskStepReq) {

		// 校验步骤是否存在
		EvalTaskStep evalTaskStep = evalTaskStepService.getTaskStep(evalTaskStepReq.getStepId());

		// 关闭的任务步骤不允许操作
		if (!evalTaskMapper.isNotFinished(evalTaskStep.getTaskId())) {
			throw new BusinessException(CrBusinessErrorCode.EVAL_TASK_NOT_RUNNING);
		}

		Boolean result = evalTaskStepService.startStep(evalTaskStep);
		return new BaseRsp<>(result);
	}

	@ApiOperation("完成步骤")
	@PostMapping("/step/finish")
	public BaseRsp<Boolean> finishStep(@RequestBody @Validated EvalTaskStepReq evalTaskStepReq) {

		// 校验步骤是否存在
		EvalTaskStep evalTaskStep = evalTaskStepService.getTaskStep(evalTaskStepReq.getStepId());

		// 关闭的任务步骤不允许操作
		if (!evalTaskMapper.isNotFinished(evalTaskStep.getTaskId())) {
			throw new BusinessException(CrBusinessErrorCode.EVAL_TASK_NOT_RUNNING);
		}

		Boolean result = evalTaskStepService.stopStep(evalTaskStep);
		return new BaseRsp<>(result);

	}

	@ApiOperation("启动测评任务")
	@PostMapping("/start")
	public BaseRsp<Boolean> startEvalTask(@RequestBody @Validated EvalTaskStartReq evalTaskStartReq) {

		String templateId = evalTaskStartReq.getTemplateId();

		// 启动前校验当前模板下是否存在运行中任务
		if (evalTemplateService.validateExistTask(templateId, EvalTask.Status.RUNNING)) {
			throw new BusinessException(CrBusinessErrorCode.RUNNING_EVAL_TASK_EXIST);
		}

		EvalTemplate evalTemplate = evalTemplateService.getTemplate(templateId);

		evalTaskService.startTask(evalTemplate);

		return new BaseRsp<>(true);
	}

	@ApiOperation("关闭测评任务")
	@PostMapping("/stop")
	public BaseRsp<Boolean> stopEvalTask(@RequestBody @Validated EvalTaskStopReq evalTaskStopReq) {

		EvalTask evalTask = evalTaskService.getTask(evalTaskStopReq.getTaskId());

		String taskName = evalTaskStopReq.getTaskName();

		if (taskName == null) {
			// 删除场景及任务
			evalTaskService.deleteTask(evalTask);
		} else {
			// 关闭场景及任务
			CrCommonValidator.validateNameLen(taskName, CrBusinessErrorCode.ILLEGAL_NAME_LEN);
			// 校验名称重复 todo

			evalTask.setTaskName(taskName);
			evalTaskService.stopTask(evalTask);
		}

		// 更新模板当前任务id
		EvalTemplate evalTemplate = evalTemplateService.getById(evalTaskStopReq.getTemplateId());
		evalTemplate.setCurrentTaskId("");
		evalTemplateService.updateById(evalTemplate);

		return new BaseRsp<>(true);
	}

	@ApiOperation("查询测评任务")
	@GetMapping("")
	public BaseRsp<EvalTaskRsp> getEvalTask(
			@ApiParam(name = "template_id", value = "测评靶场模板id",
					required = true) @RequestParam("template_id") String templateId,
			@ApiParam(name = "task_id", value = "测评任务id", required = false) @RequestParam(
					value = "task_id", required = false) String taskId) {

		EvalTemplate evalTemplate = evalTemplateService.getTemplate(templateId);

		// task_id未赋值时。默认返回当前实例
		String currentTaskId = evalTemplate.getCurrentTaskId();
		if (taskId == null && currentTaskId != null && !currentTaskId.isEmpty()) {
			taskId = currentTaskId;
		}

		EvalTaskRsp evalTaskRsp = new EvalTaskRsp();
		evalTaskRsp.setTemplateId(evalTemplate.getResourceId());
		evalTaskRsp.setTemplateName(evalTemplate.getName());
		SceneViewContext sceneViewContext = null;

		if (taskId == null) {
			sceneViewContext = evalSceneService.viewScene(null, evalTemplate.getTopoId());

		} else {
			EvalTask evalTask = evalTaskService.getById(taskId);
			sceneViewContext = evalSceneService.viewScene(evalTask.getMpSceneId(),
					evalTask.getCopyedTopoId());
			evalTaskRsp.setTaskId(taskId);
			evalTaskRsp.setTaskName(evalTask.getTaskName());
			sceneViewContext.setLocalSceneId(evalTask.getLocalSceneId());
		}

		BeanUtils.copyProperties(sceneViewContext, evalTaskRsp);
		return new BaseRsp<>(evalTaskRsp);
	}

	@ApiOperation("删除测评任务")
	@DeleteMapping("")
	public BaseRsp<Boolean> deleteEvalTask(@RequestBody @Validated EvalTaskDeleteReq evalTaskDeleteReq) {

		for (String taskId : evalTaskDeleteReq.getTaskIds()) {
			evalTaskService.deleteTask(evalTaskService.getById(taskId));
		}

		return new BaseRsp<>(true);
	}

	@ApiOperation("查询测评任务步骤")
	@GetMapping("/step/list")
	public BaseRsp<List<EvalTaskStepContext>> getStepList(
			@ApiParam(name = "template_id", value = "测评靶场模板id",
					required = true) @RequestParam("template_id") String templateId,
			@ApiParam(name = "task_id", value = "测评任务id", required = false) @RequestParam(
					value = "task_id", required = false) String taskId) {

		if (taskId == null) {
			taskId = evalTemplateService.getById(templateId).getCurrentTaskId();
		}

		List<EvalTaskStepContext> evalTaskStepContextList = evalTaskStepService
				.getTaskSteps(taskId);

		return new BaseRsp<>(evalTaskStepContextList);
	}

	@ApiOperation("查询测评任务时间线")
	@GetMapping("/time_line")
	public BaseRsp<EvalTaskTimeLineRsp> getTimeLine(
			@ApiParam(name = "template_id", value = "测评靶场模板id",
					required = true) @RequestParam("template_id") String templateId,
			@ApiParam(name = "task_id", value = "测评任务id", required = false) @RequestParam(
					value = "task_id", required = false) String taskId) {

		if (taskId == null) {
			taskId = evalTemplateService.getById(templateId).getCurrentTaskId();
		}

		// 处理没有taskId的情况 todo

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