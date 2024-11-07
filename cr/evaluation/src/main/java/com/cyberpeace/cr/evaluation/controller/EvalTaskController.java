package com.cyberpeace.cr.evaluation.controller;

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
import com.cyberpeace.cr.evaluation.service.EvalSceneService;
import com.cyberpeace.cr.evaluation.service.EvalTaskService;
import com.cyberpeace.cr.evaluation.service.EvalTaskStepService;
import com.cyberpeace.cr.evaluation.service.EvalTemplateService;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskOpsContext;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskStepContext;
import com.cyberpeace.cr.evaluation.service.context.EvalTimeLineContext;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * 评估任务控制器
 *
 * @author SN
 * @date 2024/11/07
 */
@Api(tags = "靶场测评-任务")
@Slf4j
@RestController
@RequiresAuthentication
@RequestMapping("/cr/eval/task")
public class EvalTaskController {

	/**
	 * 评估模板Service
	 */
	@Autowired
	private EvalTemplateService evalTemplateService;

	/**
	 * 评估任务Service
	 */
	@Autowired
	private EvalTaskService evalTaskService;

	/**
	 * 评估场景Service
	 */
	@Autowired
	private EvalSceneService evalSceneService;

	/**
	 * 评估任务步Service
	 */
	@Autowired
	private EvalTaskStepService evalTaskStepService;

	/**
	 * 开始步骤
	 *
	 * @param evalTaskStepReq 评估任务步请求体
	 * @return {@code BaseRsp<Boolean> }
	 */
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

	/**
	 * 完成步骤
	 *
	 * @param evalTaskStepReq 评估任务步请求体
	 * @return {@code BaseRsp<Boolean> }
	 */
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

	/**
	 * 启动 EVAL 任务
	 *
	 * @param evalTaskStartReq 评估任务开始请求体
	 * @return {@code BaseRsp<EvalTaskIdRsp> }
	 */
	@ApiOperation("启动测评任务")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@PostMapping("/start")
	public BaseRsp<EvalTaskIdRsp>
			startEvalTask(@RequestBody @Validated EvalTaskStartReq evalTaskStartReq) {

		String templateId = evalTaskStartReq.getTemplateId();

		// 启动前校验当前模板下是否存在运行中任务
		if (evalTemplateService.validateExistTask(templateId, EvalTask.Status.RUNNING)) {
			log.error("The evaluation range has running task! templateId = [{}]",templateId);
			throw new BusinessException(CrBusinessErrorCode.RUNNING_EVAL_TASK_EXIST);
		}

		EvalTemplate evalTemplate = evalTemplateService.getTemplate(templateId);

		String taskId = evalTaskService.startTask(evalTemplate);

		return new BaseRsp<>(new EvalTaskIdRsp(taskId));
	}

	/**
	 * 停止评估任务
	 *
	 * @param evalTaskStopReq 评估任务停请求体
	 * @return {@code BaseRsp<Boolean> }
	 */
	@ApiOperation("关闭测评任务")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@PostMapping("/stop")
	public BaseRsp<Boolean> stopEvalTask(@RequestBody @Validated EvalTaskStopReq evalTaskStopReq) {

		EvalTaskOpsContext evalTaskOpsContext = evalTaskService
				.getEvalTaskOpsContext(evalTaskStopReq.getTaskId(), true);
		EvalTask evalTask = evalTaskOpsContext.getEvalTask();
		String taskName = evalTaskStopReq.getTaskName();

		// 没有任务名称时，删除场景及任务
		if (taskName == null) {
			return new BaseRsp<>(evalTaskService.deleteTask(evalTaskOpsContext));
		}

		// 存在任务名称时，关闭任务场景
		if (evalTaskService.validateName(taskName, evalTask.getTemplateId(),
				evalTask.getResourceId())) {
			evalTask.setTaskName(taskName);
			return new BaseRsp<>(evalTaskService.stopTask(evalTaskOpsContext));
		}

		return new BaseRsp<>(false);
	}

	/**
	 * 获取评估任务
	 *
	 * @param templateId 模板id
	 * @param taskId     任务id
	 * @return {@code BaseRsp<EvalTaskRsp> }
	 */
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

	/**
	 * 删除 eval 任务
	 *
	 * @param evalTaskDeleteReq 评估任务删除请求体
	 * @return {@code BaseRsp<Boolean> }
	 */
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

	/**
	 * 获取步列表
	 *
	 * @param templateId 模板id
	 * @param taskId     任务id
	 * @return {@code BaseRsp<List<EvalTaskStepContext>> }
	 */
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

	/**
	 * 获取时间线
	 *
	 * @param templateId 模板id
	 * @param taskId     任务id
	 * @return {@code BaseRsp<EvalTaskTimeLineRsp> }
	 */
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

	/**
	 * 获取时间节点
	 *
	 * @param templateId 模板id
	 * @param taskId     任务id
	 * @return {@code BaseRsp<EvalTaskTimeNodesRsp> }
	 */
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

		EvalTask evalTask = evalTaskService.getTask(taskId);
		List<EvalTimeLineContext> evalTimeLineContextList = evalTaskStepService
				.getFinishedSteps(taskId);
		evalTimeLineContextList.add(0, new EvalTimeLineContext(TrainingReport.TemplateI18n.scene_start.getMsg(), evalTask.getStartTime()));
		evalTimeLineContextList.add(new EvalTimeLineContext(TrainingReport.TemplateI18n.scene_end.getMsg(), evalTask.getEndTime()));
		evalTaskTimeNodesRsp.setData(evalTimeLineContextList);

		return new BaseRsp<>(evalTaskTimeNodesRsp);
	}

}