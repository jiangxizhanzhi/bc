package com.cyberpeace.cr.evaluation.controller;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.service.CrSceneService;
import com.cyberpeace.cr.evaluation.controller.req.EvalFlowsOpsReq;
import com.cyberpeace.cr.evaluation.controller.req.FlowParamUpdateReq;
import com.cyberpeace.cr.evaluation.controller.rsp.FlowDownloadRsp;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;
import com.cyberpeace.cr.evaluation.service.EvalSceneService;
import com.cyberpeace.cr.evaluation.service.EvalTaskService;
import com.cyberpeace.cr.evaluation.service.context.EvalTaskFlowListQuery;
import com.cyberpeace.cs.service.context.scene.ElementFlow;
import com.cyberpeace.integratation.model.scene.FlowOpsContext;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * EVAL 任务流控制器
 *
 * @author SN
 * @date 2024/11/07
 */
@Slf4j
@Api(tags = "靶场测评-流量")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/eval/task/flow")
public class EvalTaskFlowController {

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
	 * 靶场场景Service
	 */
	@Autowired
	private CrSceneService crSceneService;

	/**
	 * 获取流列表
	 *
	 * @param query 查询
	 * @return {@code BaseRsp<ListRsp<ElementFlow>> }
	 */
	@ApiOperation("获取任务流量列表")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@GetMapping("/list")
	public BaseRsp<ListRsp<ElementFlow>> getFlowList(@Validated EvalTaskFlowListQuery query) {

		ListRsp<ElementFlow> flowListRsp = PageUtil.getNullResult();

		// 参数校验,任务id和节点id为空时返回空数据
		if (StringUtils.isEmpty(query.getTaskId()) || StringUtils.isEmpty(query.getNodeId())) {
			return new BaseRsp<>(flowListRsp);
		}

		// 鉴权
		EvalTask evalTask = evalTaskService.getTask(query.getTaskId());

		// 关闭的任务直接返回空数据
		if (evalTask.getEndTime() != null) {
			return new BaseRsp<>(flowListRsp);
		}

		flowListRsp = crSceneService
				.getCrFlowList(evalSceneService.getElementFlowListContext(query, evalTask));

		return new BaseRsp<>(flowListRsp);
	}

	/**
	 * Update Flow 参数
	 *
	 * @param flowParamUpdateReq 流参数更新请求体
	 * @return {@code BaseRsp<Boolean> }
	 */
	@ApiOperation("修改节点流量的源IP")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@PostMapping("/source_ip")
	public BaseRsp<Boolean>
			updateFlowParam(@RequestBody @Validated FlowParamUpdateReq flowParamUpdateReq) {

		// 鉴权并判断任务是否为活动状态
		EvalTask evalTask = evalTaskService.getActiveTask(flowParamUpdateReq.getTaskId());

		// 请求中台接口修改流量信息
		FlowOpsContext flowOpsContext = BeanUtils.copy(flowParamUpdateReq, FlowOpsContext.class);
		flowOpsContext.setId(evalTask.getMpSceneId());
		Boolean result = crSceneService.updateFlowParam(flowOpsContext);

		return new BaseRsp<>(result);
	}

	/**
	 * 启动所有流
	 *
	 * @param evalFlowsOpsReq 评估流老年 退休金 计划请求体
	 * @return {@code BaseRsp<Boolean> }
	 */
	@ApiOperation("启动全部任务流量")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@PostMapping("/all_start")
	public BaseRsp<Boolean> startAllFlow(@RequestBody @Validated EvalFlowsOpsReq evalFlowsOpsReq) {

		// 鉴权并判断任务是否为活动状态
		EvalTask evalTask = evalTaskService.getActiveTask(evalFlowsOpsReq.getTaskId());

		Boolean result = crSceneService.startAllFlows(evalTask.getMpSceneId(),
				evalFlowsOpsReq.getNodeId(), evalFlowsOpsReq.getRunType(),
				evalTask.getResourceId());

		return new BaseRsp<>(result);
	}

	/**
	 * 启动流程列表
	 *
	 * @param evalFlowsOpsReq 评估流老年 退休金 计划请求体
	 * @return {@code BaseRsp<Boolean> }
	 */
	@ApiOperation("批量启动任务流量")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@PostMapping("/batch_start")
	public BaseRsp<Boolean> startFlowList(@RequestBody @Validated EvalFlowsOpsReq evalFlowsOpsReq) {

		// 鉴权并判断任务是否为活动状态
		EvalTask evalTask = evalTaskService.getActiveTask(evalFlowsOpsReq.getTaskId());

		// 启动流量
		Boolean result = crSceneService.startFlows(evalTask.getMpSceneId(),
				evalFlowsOpsReq.getNodeId(), evalFlowsOpsReq.getFlows(), evalTask.getResourceId());
		return new BaseRsp<>(result);
	}

	/**
	 * 下载流程
	 *
	 * @param taskId 任务id
	 * @param flowId 流id
	 * @return {@code BaseRsp<FlowDownloadRsp> }
	 */
	@ApiOperation("获取流量下载链接")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@GetMapping("/download")
	public BaseRsp<FlowDownloadRsp> downloadFlow(
			@ApiParam(name = "task_id", value = "测评任务id",
					required = true) @RequestParam("task_id") String taskId,
			@ApiParam(name = "flow_id", value = "流量id",
					required = true) @RequestParam("flow_id") String flowId) {

		// 鉴权
		evalTaskService.getTask(taskId);

		// 获取流量下载链接
		FlowDownloadRsp flowDownloadRsp = new FlowDownloadRsp(
				crSceneService.getFlowFilePath(flowId));
		return new BaseRsp<>(flowDownloadRsp);

	}

}