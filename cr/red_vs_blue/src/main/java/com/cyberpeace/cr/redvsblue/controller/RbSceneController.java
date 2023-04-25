package com.cyberpeace.cr.redvsblue.controller;

import java.util.List;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.service.CrSceneService;
import com.cyberpeace.cr.redvsblue.controller.req.AllStartFlowRsp;
import com.cyberpeace.cr.redvsblue.controller.req.FlowListQuery;
import com.cyberpeace.cr.redvsblue.controller.rsp.DownloadFlowFilePathRsp;
import com.cyberpeace.cs.service.context.scene.ElementFlow;
import com.cyberpeace.integratation.model.scene.FlowOpsContext;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.controller.req.FlowOpsReq;
import com.cyberpeace.cr.crbase.controller.req.scene.SceneNodeOpsReq;
import com.cyberpeace.cr.crbase.controller.req.scene.SceneOpsReq;
import com.cyberpeace.cr.crbase.controller.rsp.StartSceneRsp;
import com.cyberpeace.cr.crbase.service.context.SceneViewContext;
import com.cyberpeace.cr.crbase.service.context.scene.Flow;
import com.cyberpeace.cr.crbase.service.context.scene.FlowRsp;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.service.RbRoomService;
import com.cyberpeace.cr.redvsblue.service.RbSceneService;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.service.RemoteLogService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "红蓝对抗场景")
@Slf4j
@RestController
@RequiresAuthentication
@RequestMapping("/cr/red_vs_blue/scene/")
public class RbSceneController {

	@Autowired
	private RbRoomService rbRoomService;

	@Autowired
	private RbSceneService rbSceneService;

	@Autowired
	private LocalSceneService localSceneService;

	@Autowired
	private RemoteLogService remoteLogService;

	@Autowired
	private CrSceneService crSceneService;

	@ApiOperation("启动场景")
	@PostMapping("")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp startScene(@RequestBody SceneOpsReq sceneStartReq) {
		String roomId = sceneStartReq.getRoomId();
		String userId = UserInfoContext.getUserContext().getUserId();

		String sceneId = rbSceneService.startScene(roomId, userId);
		return new BaseRsp(new StartSceneRsp(sceneId));
	}

	@ApiOperation("停止场景")
	@DeleteMapping("")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp stopScene(@RequestBody SceneOpsReq sceneStopReq) {
		String roomId = sceneStopReq.getRoomId();

		Boolean result = rbSceneService.stopScene(roomId);
		return new BaseRsp(result);
	}

	@ApiOperation("查看场景")
	@GetMapping("")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<SceneViewContext> getScene(
			@ApiParam(name = "room_id", value = "红蓝对抗实例id", required = true) String room_id,
			@ApiParam(name = "role_id", value = "角色id", required = false) String role_id) {
		SceneViewContext context = rbSceneService.viewScene(room_id, role_id);

		return new BaseRsp<>(context);
	}

	@ApiOperation("暂停场景")
	@PostMapping("/pause")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<Boolean> pauseScene(@RequestBody SceneOpsReq sceneOpsReq) {
		String roomId = sceneOpsReq.getRoomId();

		Boolean ret = rbSceneService.pauseScene(roomId);

		// 记录远程操作日志
		remoteLogService.addCrLog(UserInfoContext.getUserContext(), OperationModule.RED_VS_BLUE,
				OperationType.PAUSE, rbRoomService.getById(roomId).getName());

		return new BaseRsp<>(ret);
	}

	@ApiOperation("从暂停中恢复场景")
	@PostMapping("/unpause")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<Boolean> unpauseScene(@RequestBody SceneOpsReq sceneOpsReq) {
		String roomId = sceneOpsReq.getRoomId();

		Boolean ret = rbSceneService.unpauseScene(roomId);

		// 记录远程操作日志
		remoteLogService.addCrLog(UserInfoContext.getUserContext(), OperationModule.RED_VS_BLUE,
				OperationType.UNPAUSE, rbRoomService.getById(roomId).getName());

		return new BaseRsp<>(ret);
	}

	@ApiOperation("查询流量")
	@GetMapping("flow")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<FlowRsp> getFlowList(
			@ApiParam(name = "room_id", value = "团队训练id", required = true) String room_id) {

		List<Flow> flows = rbSceneService.getFlowList(room_id);
		FlowRsp ret = new FlowRsp();
		ret.setData(flows);

		return new BaseRsp<>(ret);
	}

	@ApiOperation("分页查询节点流量")
	@GetMapping("flow/node")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<ListRsp<ElementFlow>> getNodeFlowList(FlowListQuery flowListQuery) {
		// 无效入参返回
		if (StringUtils.isEmpty(flowListQuery.getRoomId()) || StringUtils.isEmpty(flowListQuery.getNodeId())) {
			return new BaseRsp<>(PageUtil.getListResult(Lists.newArrayList(), flowListQuery.toPageParam(), 0));
		}

		RbRoom rbRoom = rbRoomService.getById(flowListQuery.getRoomId());
		LocalScene localScene = localSceneService.getById(rbRoom.getLocalSceneId());

		return new BaseRsp<>(crSceneService.getCrFlowList(
				localScene != null ? localScene.getMpSceneId() : null,
				rbRoom.getCopyedTopoId(),
				flowListQuery.getNodeId(),
				flowListQuery.getInteractiveType(),
				flowListQuery.toPageParam()
		));
	}

	@ApiOperation("启动流量")
	@PostMapping("/flow")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp startFlow(@RequestBody FlowOpsReq req) {
		String roomId = req.getRoomId();
		String userId = UserInfoContext.getUserContext().getUserId();
		// 1、鉴权
		rbRoomService.checkDirectorPermission(roomId, userId);
		// 2、确认场景已开启
		LocalScene localScene = localSceneService.getByBusinessInstanceId(roomId);
		if (!localSceneService.ensureSceneStarted(localScene)) {
			throw new BusinessException(CrBusinessErrorCode.SCENE_NOT_STARTED);
		}
		// 3、开启流量
		Boolean result = localSceneService.startFlow(localScene, req.getNodeId(), req.getFlowId());
		if (!result) {
			throw new BusinessException(CrBusinessErrorCode.START_FLOW_FAILURE);
		}
		return new BaseRsp();
	}

	@ApiOperation("停止流量")
	@PostMapping("/flow/stop")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp stopFlow(@RequestBody FlowOpsReq req) {
		String roomId = req.getRoomId();
		String userId = UserInfoContext.getUserContext().getUserId();
		// 1、鉴权
		rbRoomService.checkDirectorPermission(roomId, userId);
		// 2、确认场景已开启
		LocalScene localScene = localSceneService.getByBusinessInstanceId(roomId);
		if (!localSceneService.ensureSceneStarted(localScene)) {
			throw new BusinessException(CrBusinessErrorCode.SCENE_NOT_STARTED);
		}
		// 3、停止流量
		Boolean result = localSceneService.stopFlow(localScene, req.getNodeId(), req.getFlowId());
		if (!result) {
			throw new BusinessException(CrBusinessErrorCode.STOP_FLOW_FAILURE);
		}
		return new BaseRsp();
	}

	@ApiOperation("更新源IP")
	@PostMapping("/flow/source_ip")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<Boolean> updateFlowSourceIp(@RequestBody FlowOpsReq req) {

		LocalScene localScene = localSceneService.getByBusinessInstanceId(req.getRoomId());
		if (localScene == null) {
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}

		FlowOpsContext flowOpsContext = BeanUtils.copy(req, FlowOpsContext.class);
		flowOpsContext.setId(localScene.getMpSceneId());
		return new BaseRsp<>(crSceneService.updateFlowParam(flowOpsContext));
	}

	@ApiOperation("下载流量文件")
	@GetMapping("/flow/download")
	public BaseRsp<DownloadFlowFilePathRsp> downloadFlow(
			@ApiParam(name = "flow_id", value = "流量id", required = true)
			@RequestParam("flow_id") String flowId
	) {
		DownloadFlowFilePathRsp downloadFlowFilePathRsp = new DownloadFlowFilePathRsp();
		downloadFlowFilePathRsp.setFilePath(crSceneService.getFlowFilePath(flowId));

		return new BaseRsp<>(downloadFlowFilePathRsp);
	}

	@ApiOperation("节点流量全部启动")
	@PostMapping("/all_start")
	public BaseRsp<Boolean> allStartFlow(@RequestBody @Validated AllStartFlowRsp allStartFlowRsp) {
		RbRoom rbRoom = rbRoomService.getById(allStartFlowRsp.getRoomId());
		if (rbRoom == null) {
			throw new BusinessException("非法请求");
		}

		LocalScene localScene = localSceneService.getById(rbRoom.getLocalSceneId());
		if (localScene == null) {
			throw new BusinessException("非法请求");
		}

		Boolean result = crSceneService.startAllFlows(
				localScene.getMpSceneId(),
				allStartFlowRsp.getNodeId(),
				allStartFlowRsp.getRunType(),
				allStartFlowRsp.getRoomId()
		);
		return new BaseRsp<>(result);
	}

	@ApiOperation("重启节点")
	@PostMapping("/node/restart")
	public BaseRsp restartNode(@RequestBody SceneNodeOpsReq nodeOpsReq) {
		RbRoom room = rbRoomService.getById(nodeOpsReq.getResourceId());
		if (room == null || StringUtils.isBlank(room.getLocalSceneId())) {
			throw new BusinessException(CrBusinessErrorCode.SCENE_NOT_STARTED);
		}
		Boolean result = localSceneService.restartNode(room.getLocalSceneId(),
				nodeOpsReq.getNodeId());
		if (!result) {
			throw new BusinessException(CrBusinessErrorCode.FAILED_TO_RESTART_NODE);
		}
		return new BaseRsp();
	}

	@ApiOperation("重建节点")
	@PostMapping("/node/recreate")
	public BaseRsp recreateNode(@RequestBody SceneNodeOpsReq nodeOpsReq) {
		RbRoom room = rbRoomService.getById(nodeOpsReq.getResourceId());
		if (room == null || StringUtils.isBlank(room.getLocalSceneId())) {
			throw new BusinessException(CrBusinessErrorCode.SCENE_NOT_STARTED);
		}
		Boolean result = localSceneService.recreateNode(room.getLocalSceneId(),
				nodeOpsReq.getNodeId());
		if (!result) {
			throw new BusinessException(CrBusinessErrorCode.FAILED_TO_RECREATE_NODE);
		}
		return new BaseRsp();
	}

	@ApiOperation("创建启动节点")
	@PostMapping("/node/create")
	public BaseRsp createNode(@RequestBody SceneNodeOpsReq nodeOpsReq) {
		RbRoom room = rbRoomService.getById(nodeOpsReq.getResourceId());
		if (room == null || StringUtils.isBlank(room.getLocalSceneId())) {
			throw new BusinessException(CrBusinessErrorCode.SCENE_NOT_STARTED);
		}
		Boolean result = localSceneService.createNode(room.getLocalSceneId(),
				nodeOpsReq.getNodeId());
		if (!result) {
			throw new BusinessException(CrBusinessErrorCode.FAILED_TO_CREATE_NODE);
		}
		return new BaseRsp();
	}
}
