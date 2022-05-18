package com.cyberpeace.cr.evaluation.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cr.crbase.service.context.scene.FlowElementFlow;
import com.cyberpeace.cr.evaluation.controller.req.FlowChangeReq;
import com.cyberpeace.cr.evaluation.controller.req.FlowsBatchOpsReq;
import com.cyberpeace.cr.evaluation.controller.req.FlowsOpsReq;
import com.cyberpeace.cr.evaluation.controller.rsp.FlowDownloadRsp;
import com.cyberpeace.cr.evaluation.controller.rsp.ListFlowRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;
import com.cyberpeace.cr.evaluation.service.EvalSceneService;
import com.cyberpeace.cr.evaluation.service.EvalTaskService;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskFlowListQuery;
import com.cyberpeace.integratation.model.mp.MpFlow;
import com.cyberpeace.integratation.service.RemoteSceneService;

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

	@Autowired
	private RemoteSceneService remoteSceneService;

	@ApiOperation("获取任务流量列表")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@GetMapping("/list")
	public BaseRsp<ListFlowRsp<FlowElementFlow>>
			getFlowList(@Validated EvalTaskFlowListQuery evalTaskFlowListQuery) {

		// 参数校验
		if (StringUtils.isEmpty(evalTaskFlowListQuery.getTaskId())
				|| StringUtils.isEmpty(evalTaskFlowListQuery.getNodeId())) {
			return new BaseRsp<>(new ListFlowRsp<FlowElementFlow>());
		}

		// 鉴权
		EvalTask evalTask = evalTaskService.getTask(evalTaskFlowListQuery.getTaskId());
		Page page = evalTaskFlowListQuery.toPageParam();
		ListFlowRsp<FlowElementFlow> flowListRsp = evalSceneService.getFlowList(evalTask,
				evalTaskFlowListQuery.getNodeId(), page);
		return new BaseRsp<>(flowListRsp);
	}

	@ApiOperation("修改节点流量源IP")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@PostMapping("/source_ip")
	public BaseRsp<Boolean>
			changeFlowSourceIp(@RequestBody @Validated FlowChangeReq flowChangeReq) {

		// 鉴权
		EvalTask evalTask = evalTaskService.getTask(flowChangeReq.getTaskId());
		return new BaseRsp<>(true);
	}

	@ApiOperation("启动全部任务流量")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@PostMapping("/all_start")
	public BaseRsp<Boolean> startAllFlow(@RequestBody @Validated FlowsOpsReq flowsOpsReq) {

		// 鉴权
		EvalTask evalTask = evalTaskService.getTask(flowsOpsReq.getTaskId());
		return new BaseRsp<>(true);
	}

	// @ApiOperation("停止全部任务流量")
	// @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	// @PostMapping("/all_stop")
	// public BaseRsp<Boolean> stopAllFlow(@RequestBody @Validated FlowsOpsReq
	// flowsOpsReq) {
	//
	// // 鉴权
	// EvalTask evalTask = evalTaskService.getTask(flowsOpsReq.getTaskId());
	// return new BaseRsp<>(true);
	// }

	@ApiOperation("批量启动任务流量")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@PostMapping("/batch_start")
	public BaseRsp<Boolean> startFlowList(@RequestBody @Validated FlowsBatchOpsReq flowsOpsReq) {

		// 鉴权
		EvalTask evalTask = evalTaskService.getTask(flowsOpsReq.getTaskId());
		return new BaseRsp<>(true);
	}

	// @ApiOperation("批量停止任务流量")
	// @RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	// @PostMapping("/batch_stop")
	// public BaseRsp<Boolean> stopFlowList(@RequestBody @Validated FlowsBatchOpsReq
	// flowsOpsReq) {
	//
	// // 鉴权
	// EvalTask evalTask = evalTaskService.getTask(flowsOpsReq.getTaskId());
	// return new BaseRsp<>(true);
	// }

	@ApiOperation("下载流量")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@GetMapping("/download")
	public BaseRsp<FlowDownloadRsp> downloadFlow(
			@ApiParam(name = "task_id", value = "测评任务id",
					required = true) @RequestParam("task_id") String taskId,
			@ApiParam(name = "flow_id", value = "流量id",
					required = true) @RequestParam("flow_id") String flowId) {

		// 鉴权
		evalTaskService.getTask(taskId);

		MpFlow flow = remoteSceneService.getFlow(flowId);
		Integer resourceType = flow.getResourceType();
		String filePath = resourceType.equals(MpFlow.resourceTypeEnum.PCAP.getCode())
				? flow.getPcapFile()
				: flow.getScriptFile();
		return new BaseRsp<>(new FlowDownloadRsp(filePath));
	}

}