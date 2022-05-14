package com.cyberpeace.cr.evaluation.controller;

import java.util.Date;
import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.service.context.SceneViewContext;
import com.cyberpeace.cr.evaluation.controller.req.*;
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
import com.cyberpeace.cr.evaluation.service.context.EvalTaskOperContext;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskStepContext;

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
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@PostMapping("/step/start")
	public BaseRsp<Boolean> startStep(@RequestBody @Validated EvalTaskStepReq evalTaskStepReq) {

		// 校验步骤是否存在
		EvalTaskStep evalTaskStep = evalTaskStepService.getTaskStep(evalTaskStepReq.getStepId());

		Boolean result = evalTaskStepService.startStep(evalTaskStep);

		return new BaseRsp<>(result);
	}

	@ApiOperation("完成步骤")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@PostMapping("/step/finish")
	public BaseRsp<Boolean> finishStep(@RequestBody @Validated EvalTaskStepReq evalTaskStepReq) {

		// 校验步骤是否存在
		EvalTaskStep evalTaskStep = evalTaskStepService.getTaskStep(evalTaskStepReq.getStepId());

		Boolean result = evalTaskStepService.stopStep(evalTaskStep);

		return new BaseRsp<>(result);

	}

	@ApiOperation("启动测评任务")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@PostMapping("/start")
	public BaseRsp<Boolean>
			startEvalTask(@RequestBody @Validated EvalTaskStartReq evalTaskStartReq) {

		String templateId = evalTaskStartReq.getTemplateId();

		// 启动前校验当前模板下是否存在运行中任务
		if (evalTemplateService.validateExistTask(templateId, EvalTask.Status.RUNNING)) {
			throw new BusinessException(CrBusinessErrorCode.RUNNING_EVAL_TASK_EXIST);
		}

		EvalTemplate evalTemplate = evalTemplateService.getTemplate(templateId);

		Boolean result = evalTaskService.startTask(evalTemplate);

		return new BaseRsp<>(result);
	}

	@ApiOperation("关闭测评任务")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@PostMapping("/stop")
	public BaseRsp<Boolean> stopEvalTask(@RequestBody @Validated EvalTaskStopReq evalTaskStopReq) {

		EvalTaskOperContext evalTaskOperContext = evalTaskService
				.getEvalTaskOperContext(evalTaskStopReq.getTaskId());
		EvalTask evalTask = evalTaskOperContext.getEvalTask();
		String taskName = evalTaskStopReq.getTaskName();
		Boolean result = null;

		// 删除场景及任务
		if (taskName == null) {
			result = evalTaskService.deleteTask(evalTaskOperContext);
		}

		// 关闭任务场景
		if (evalTaskService.validateName(taskName, evalTask.getTemplateId(),
				evalTask.getResourceId())) {
			evalTask.setTaskName(taskName);
			result = evalTaskService.stopTask(evalTaskOperContext);
		}

		return new BaseRsp<>(result);
	}

	@ApiOperation("查询测评任务")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
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
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@DeleteMapping("")
	public BaseRsp<Boolean>
			deleteEvalTask(@RequestBody @Validated EvalTaskDeleteReq evalTaskDeleteReq) {

		for (String taskId : evalTaskDeleteReq.getTaskIds()) {
			evalTaskService.deleteTask(evalTaskService.getEvalTaskOperContext(taskId));
		}

		return new BaseRsp<>(true);
	}

	@ApiOperation("查询测评任务步骤")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
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
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@GetMapping("/time_line")
	public BaseRsp<EvalTaskTimeLineRsp> getTimeLine(
			@ApiParam(name = "template_id", value = "测评靶场模板id",
					required = true) @RequestParam("template_id") String templateId,
			@ApiParam(name = "task_id", value = "测评任务id", required = false) @RequestParam(
					value = "task_id", required = false) String taskId) {

		// 未传入任务Id时提取当前活动任务
		if (taskId == null) {
			taskId = evalTemplateService.getById(templateId).getCurrentTaskId();
		}

		// 构建返回数据
		EvalTaskTimeLineRsp evalTaskTimeLineRsp = new EvalTaskTimeLineRsp();
		evalTaskTimeLineRsp.setTaskId(taskId);
		evalTaskTimeLineRsp.setCurrentTime(new Date());

		// 处理模板下无任务的情况
		if (taskId == null) {
			return new BaseRsp<>(evalTaskTimeLineRsp);
		}

		EvalTask evalTask = evalTaskService.getTask(taskId);
		evalTaskTimeLineRsp.setData(evalTaskStepService.getFinishedSteps(taskId));
		evalTaskTimeLineRsp.setStartTime(evalTask.getStartTime());
		evalTaskTimeLineRsp.setEndTime(evalTask.getEndTime());

		return new BaseRsp<>(evalTaskTimeLineRsp);
	}

	@ApiOperation("任务名称校验")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@PostMapping("validate_name")
	public BaseRsp<Boolean>
			validateName(@RequestBody @Validated EvalTaskNameValidateReq evalTaskNameValidateReq) {
		Boolean result = evalTaskService.validateName(evalTaskNameValidateReq.getTaskName(),
				evalTaskNameValidateReq.getTemplateId(), evalTaskNameValidateReq.getTaskId());
		return new BaseRsp<>(result);
	}

}