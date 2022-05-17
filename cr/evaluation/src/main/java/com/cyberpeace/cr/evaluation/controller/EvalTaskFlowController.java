package com.cyberpeace.cr.evaluation.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.util.FileUtil;
import com.cyberpeace.cr.crbase.service.context.scene.Flow;
import com.cyberpeace.cr.crbase.service.context.scene.FlowRsp;
import com.cyberpeace.cr.evaluation.controller.req.FlowDownloadReq;
import com.cyberpeace.cr.evaluation.controller.req.FlowsBatchOpsReq;
import com.cyberpeace.cr.evaluation.controller.req.FlowsOpsReq;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;
import com.cyberpeace.cr.evaluation.service.EvalSceneService;
import com.cyberpeace.cr.evaluation.service.EvalTaskService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Api(tags = "靶场测评-流量")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/eval/task/flow")
public class EvalTaskFlowController {

	@Autowired
	private EvalTaskService evalTaskService;

	@Autowired
	private EvalSceneService evalSceneService;

	@ApiOperation("获取任务流量列表")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@GetMapping("/list")
	public BaseRsp<FlowRsp> getFlowList(@ApiParam(name = "task_id", value = "测评任务id",
			required = true) @RequestParam("task_id") String taskId) {

		// 鉴权
		EvalTask evalTask = evalTaskService.getTask(taskId);
		List<Flow> flows = evalSceneService.getFlowList(evalTask);

		return new BaseRsp<>(new FlowRsp(flows));
	}

	@ApiOperation("启动全部任务流量")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@PostMapping("/all_start")
	public BaseRsp<Boolean> startAllFlow(@RequestBody @Validated FlowsOpsReq flowsOpsReq) {

		// 鉴权
		EvalTask evalTask = evalTaskService.getTask(flowsOpsReq.getTaskId());
		return new BaseRsp<>(true);
	}

	@ApiOperation("停止全部任务流量")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@PostMapping("/all_stop")
	public BaseRsp<Boolean> stopAllFlow(@RequestBody @Validated FlowsOpsReq flowsOpsReq) {

		// 鉴权
		EvalTask evalTask = evalTaskService.getTask(flowsOpsReq.getTaskId());
		return new BaseRsp<>(true);
	}

	@ApiOperation("批量启动任务流量")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@PostMapping("/batch_start")
	public BaseRsp<Boolean> startFlowList(@RequestBody @Validated FlowsBatchOpsReq flowsOpsReq) {

		// 鉴权
		EvalTask evalTask = evalTaskService.getTask(flowsOpsReq.getTaskId());
		return new BaseRsp<>(true);
	}

	@ApiOperation("批量停止任务流量")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@PostMapping("/batch_stop")
	public BaseRsp<Boolean> stopFlowList(@RequestBody @Validated FlowsBatchOpsReq flowsOpsReq) {

		// 鉴权
		EvalTask evalTask = evalTaskService.getTask(flowsOpsReq.getTaskId());
		return new BaseRsp<>(true);
	}

	@ApiOperation("下载流量")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@PostMapping("/download")
	public BaseRsp downloadFlow(@RequestBody @Validated FlowDownloadReq flowDownloadReq,
			HttpServletResponse response) throws FileNotFoundException {

		// 鉴权
		EvalTask evalTask = evalTaskService.getTask(flowDownloadReq.getTaskId());

		String realPath = "";

		File file = new File(realPath);
		if (!file.exists()) {
			log.error("File not found! real_path=[{}]", realPath);
			throw new FileNotFoundException(realPath);
		}

		FileUtil.setResponse(file, false, response);
		return new BaseRsp<>();
	}

}