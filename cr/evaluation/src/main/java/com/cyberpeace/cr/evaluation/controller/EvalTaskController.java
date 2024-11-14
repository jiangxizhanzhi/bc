package com.cyberpeace.cr.evaluation.controller;

import java.util.Date;
import java.util.List;

import com.cyberpeace.cr.evaluation.dao.entity.EvalTestInstance;
import com.cyberpeace.cr.evaluation.service.*;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
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
import com.cyberpeace.cr.crbase.service.context.TrainingReport;
import com.cyberpeace.cr.evaluation.controller.req.EvalTaskDeleteReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalTaskStartReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalTaskStepReq;
import com.cyberpeace.cr.evaluation.controller.req.EvalTaskStopReq;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTaskIdRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTaskRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTaskTimeLineRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.EvalTaskTimeNodesRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTaskStep;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTemplate;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskOpsContext;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskStepContext;
import com.cyberpeace.cr.evaluation.service.context.EvalTimeLineContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "靶场测评-任务")
@Slf4j
@RestController
@RequiresAuthentication
@RequestMapping("/cr/eval/task")
public class EvalTaskController {

	@Autowired
	private EvalTemplateService evalTemplateService;

	@Autowired
	private EvalTestInstanceService evalTestInstanceService;

	@Autowired
	private EvalTaskService evalTaskService;

	@Autowired
	private EvalSceneService evalSceneService;

	@Autowired
	private EvalTaskStepService evalTaskStepService;

	@ApiOperation("启动步骤")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@PostMapping("/step/start")
	public BaseRsp<Boolean> startStep(@RequestBody @Validated EvalTaskStepReq evalTaskStepReq) {

		// 校验步骤是否存在
		EvalTaskStep evalTaskStep = evalTaskStepService.getTaskStep(evalTaskStepReq.getStepId());

		// 校验用户权限
		evalTaskService.getTask(evalTaskStep.getTaskId());

		Boolean result = evalTaskStepService.startStep(evalTaskStep);

		return new BaseRsp<>(result);
	}

	@ApiOperation("完成步骤")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@PostMapping("/step/finish")
	public BaseRsp<Boolean> finishStep(@RequestBody @Validated EvalTaskStepReq evalTaskStepReq) {

		// 校验步骤是否存在
		EvalTaskStep evalTaskStep = evalTaskStepService.getTaskStep(evalTaskStepReq.getStepId());

		// 校验用户权限
		evalTaskService.getTask(evalTaskStep.getTaskId());

		Boolean result = evalTaskStepService.stopStep(evalTaskStep);

		return new BaseRsp<>(result);

	}

	@ApiOperation("启动测评任务")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@PostMapping("/start")
	public BaseRsp<EvalTaskIdRsp>
			startEvalTask(@RequestBody @Validated EvalTaskStartReq evalTaskStartReq) {

		String templateId = evalTaskStartReq.getTemplateId();

		// 启动前校验当前模板下是否存在运行中任务
		if (Boolean.TRUE.equals(
				evalTemplateService.validateExistTask(templateId, EvalTask.Status.RUNNING))) {
			log.error("The evaluation range has running task! templateId = [{}]",templateId);
			throw new BusinessException(CrBusinessErrorCode.RUNNING_EVAL_TASK_EXIST);
		}

		EvalTemplate evalTemplate = evalTemplateService.getTemplate(templateId);

		String taskId = evalTaskService.startTask(evalTemplate);

		return new BaseRsp<>(new EvalTaskIdRsp(taskId));
	}

	@ApiOperation("关闭测评任务")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@PostMapping("/stop")
	public BaseRsp<Boolean> stopEvalTask(@RequestBody @Validated EvalTaskStopReq evalTaskStopReq) {

		// 用template获取当前task，用于多用户共有测评模板的情况
		String currentTaskId = evalTemplateService.getTemplate(evalTaskStopReq.getTemplateId())
				.getCurrentTaskId();
		if (StringUtils.isNotBlank(currentTaskId)) {
			evalTaskStopReq.setTaskId(currentTaskId);
		} else {
			log.info("no active task for template: [{}]", evalTaskStopReq.getTemplateId());
			throw new BusinessException(CrBusinessErrorCode.EVAL_TASK_NOT_EXIST);
		}

		EvalTaskOpsContext evalTaskOpsContext = evalTaskService
				.getEvalTaskOpsContext(evalTaskStopReq.getTaskId(), true);
		EvalTask evalTask = evalTaskOpsContext.getEvalTask();
		String taskName = evalTaskStopReq.getTaskName();

		// 没有任务名称时，删除场景及任务
		if (taskName == null) {
			return new BaseRsp<>(evalTaskService.deleteTask(evalTaskOpsContext));
		}

		// 存在任务名称时，关闭任务场景
		if (Boolean.TRUE.equals(evalTaskService.validateName(taskName, evalTask.getTemplateId(),
				evalTask.getResourceId()))) {
			evalTask.setTaskName(taskName);
			return new BaseRsp<>(evalTaskService.stopTask(evalTaskOpsContext));
		}

		return new BaseRsp<>(false);
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
		if (StringUtils.isEmpty(taskId) && StringUtils.isNotEmpty(currentTaskId)) {
			taskId = currentTaskId;
		}

		EvalTaskRsp evalTaskRsp = new EvalTaskRsp();
		evalTaskRsp.setTemplateId(evalTemplate.getResourceId());
		evalTaskRsp.setTemplateName(evalTemplate.getName());
		SceneViewContext sceneViewContext = null;

		if (StringUtils.isEmpty(taskId)) {
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
			evalTaskService.deleteTask(evalTaskService.getEvalTaskOpsContext(taskId, true));
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

		EvalTemplate evalTemplate = evalTemplateService.getTemplate(templateId);

		if (StringUtils.isEmpty(taskId)) {
			taskId = evalTemplate.getCurrentTaskId();
		}

		List<EvalTaskStepContext> evalTaskStepContextList = Lists.newArrayList();

		// 处理模板下无任务的情况
		if (StringUtils.isEmpty(taskId)) {
			evalTaskStepContextList = evalTaskStepService
					.getTemplateSteps(evalTemplate.getStepConfig());
		} else {
			evalTaskStepContextList = evalTaskStepService.getTaskSteps(taskId);
		}

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
		if (StringUtils.isEmpty(taskId)) {
			taskId = evalTemplateService.getTemplate(templateId).getCurrentTaskId();
		}

		// 构建返回数据
		EvalTaskTimeLineRsp evalTaskTimeLineRsp = new EvalTaskTimeLineRsp();
		evalTaskTimeLineRsp.setTaskId(taskId);
		evalTaskTimeLineRsp.setCurrentTime(new Date());

		// 处理模板下无任务的情况
		if (StringUtils.isEmpty(taskId)) {
			return new BaseRsp<>(evalTaskTimeLineRsp);
		}

		EvalTask evalTask = evalTaskService.getTask(taskId);
		evalTaskTimeLineRsp.setData(evalTaskStepService.getFinishedSteps(taskId));
		evalTaskTimeLineRsp.setStartTime(evalTask.getStartTime());
		evalTaskTimeLineRsp.setEndTime(evalTask.getEndTime());

		return new BaseRsp<>(evalTaskTimeLineRsp);
	}

	@ApiOperation("查询测评任务时间节点")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@GetMapping("/time_nodes")
	public BaseRsp<EvalTaskTimeNodesRsp> getTimeNodes(
			@ApiParam(name = "template_id", value = "测评靶场模板id",
					required = true) @RequestParam("template_id") String templateId,
			@ApiParam(name = "task_id", value = "测评任务id",
					required = true) @RequestParam("task_id") String taskId) {

		// 构建返回数据
		EvalTaskTimeNodesRsp evalTaskTimeNodesRsp = new EvalTaskTimeNodesRsp();
		evalTaskTimeNodesRsp.setTaskId(taskId);

		// 评估实例对象
		EvalTestInstance evalTestInstance = evalTestInstanceService.getEvalTestInstanceByResourceId(taskId);

		List<EvalTimeLineContext> evalTimeLineContextList = evalTaskStepService
				.getFinishedSteps(taskId);
		evalTimeLineContextList.add(0, new EvalTimeLineContext(TrainingReport.TemplateI18n.scene_start.getMsg(), evalTestInstance.getStartTime()));
		evalTimeLineContextList.add(new EvalTimeLineContext(TrainingReport.TemplateI18n.scene_end.getMsg(), evalTestInstance.getEndTime()));
		evalTaskTimeNodesRsp.setData(evalTimeLineContextList);

		return new BaseRsp<>(evalTaskTimeNodesRsp);
	}

}