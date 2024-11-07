package com.cyberpeace.cr.evaluation.controller;

import com.cyberpeace.common.util.JsonUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.evaluation.controller.req.EvalSceneNodeOpsReq;
import com.cyberpeace.cr.evaluation.dao.entity.EvalTask;
import com.cyberpeace.cr.evaluation.service.EvalTaskService;
import com.cyberpeace.cs.service.LocalSceneService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

/**
 * 评估任务场景控制器
 *
 * @author SN
 * @date 2024/11/07
 */
@Api(tags = "靶场测评-场景")
@Slf4j
@RestController
@RequiresAuthentication
@RequestMapping("/cr/eval/scene")
public class EvalTaskSceneController {

	/**
	 * 评估任务Service
	 */
	@Autowired
	private EvalTaskService evalTaskService;

	/**
	 * 当地场景Service
	 */
	@Autowired
	private LocalSceneService localSceneService;

	/**
	 * 重启节点
	 *
	 * @param nodeOpsReq 节点老年 退休金 计划请求体
	 * @return {@code BaseRsp<Boolean> }
	 */
	@ApiOperation("重启节点")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@PostMapping("/node/restart")
	public BaseRsp<Boolean> restartNode(@RequestBody @Validated EvalSceneNodeOpsReq nodeOpsReq) {
		EvalTask evalTask = evalTaskService.getActiveTask(nodeOpsReq.getTaskId());
		Boolean result = localSceneService.restartNode(evalTask.getLocalSceneId(),
				nodeOpsReq.getNodeId());
		if (!result) {
			log.error("Node restart failure! nodeOpsReq = [{}]", JsonUtil.toJsonString(nodeOpsReq));
			throw new BusinessException(CrBusinessErrorCode.FAILED_TO_RESTART_NODE);
		}
		return new BaseRsp<>(result);
	}

	/**
	 * 重新创建节点
	 *
	 * @param nodeOpsReq 节点老年 退休金 计划请求体
	 * @return {@code BaseRsp<Boolean> }
	 */
	@ApiOperation("重建节点")
	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	@PostMapping("/node/recreate")
	public BaseRsp<Boolean> recreateNode(@RequestBody @Validated EvalSceneNodeOpsReq nodeOpsReq) {
		EvalTask evalTask = evalTaskService.getActiveTask(nodeOpsReq.getTaskId());
		Boolean result = localSceneService.recreateNode(evalTask.getLocalSceneId(),
				nodeOpsReq.getNodeId());
		if (!result) {
			log.error("Node recreate failure! nodeOpsReq = [{}]", JsonUtil.toJsonString(nodeOpsReq));
			throw new BusinessException(CrBusinessErrorCode.FAILED_TO_RECREATE_NODE);
		}
		return new BaseRsp<>(result);
	}

}