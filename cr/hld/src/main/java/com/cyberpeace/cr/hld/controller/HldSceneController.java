package com.cyberpeace.cr.hld.controller;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.controller.req.FlowOpsReq;
import com.cyberpeace.cr.crbase.controller.req.scene.SceneNodeOpsReq;
import com.cyberpeace.cr.crbase.controller.req.scene.SceneOpsReq;
import com.cyberpeace.cr.crbase.controller.rsp.StartSceneRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.service.context.SceneViewContext;
import com.cyberpeace.cr.crbase.service.context.scene.Flow;
import com.cyberpeace.cr.crbase.service.context.scene.FlowRsp;
import com.cyberpeace.cr.hld.dao.entity.HldRoom;
import com.cyberpeace.cr.hld.service.HldRoomService;
import com.cyberpeace.cr.hld.service.HldSceneService;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.service.LocalSceneService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "HLD场景")
@Slf4j
@RestController
@RequiresAuthentication
@RequestMapping("/cr/hld/scene/")
public class HldSceneController {

	@Autowired
	private HldRoomService hldRoomService;

	@Autowired
	private HldSceneService hldSceneService;

	@Autowired
	private LocalSceneService localSceneService;

	@ApiOperation("启动场景")
	@PostMapping("")
	public BaseRsp startScene(@RequestBody SceneOpsReq sceneStartReq) {

		String roomId = sceneStartReq.getRoomId();
		String userId = UserInfoContext.getUserContext().getUserId();
		String sceneId = hldSceneService.startScene(roomId, userId);

		return new BaseRsp(new StartSceneRsp(sceneId));
	}

	@ApiOperation("停止场景")
	@DeleteMapping("")
	public BaseRsp stopScene(@RequestBody SceneOpsReq sceneStopReq) {

		String roomId = sceneStopReq.getRoomId();
		Boolean result = hldSceneService.stopScene(roomId, true);

		return new BaseRsp(result);
	}

	@ApiOperation("查看场景")
	@GetMapping("")
	public BaseRsp<SceneViewContext> getScene(
			@ApiParam(name = "room_id", value = "HLD房间id", required = true) String room_id) {

		// 获取当前用户角色
		String userId = UserInfoContext.getUserContext().getUserId();
		CrRole role = hldRoomService.getUserRole(room_id, userId);

		// 获取角色过滤后的数据
		SceneViewContext context = hldSceneService.viewScene(room_id, role);

		return new BaseRsp<>(context);
	}

	@ApiOperation("暂停场景")
	@PostMapping("/pause")
	public BaseRsp<Boolean> pauseScene(@RequestBody SceneOpsReq sceneOpsReq) {

		String roomId = sceneOpsReq.getRoomId();
		Boolean ret = hldSceneService.pauseScene(roomId);

		return new BaseRsp<>(ret);
	}

	@ApiOperation("从暂停中恢复场景")
	@PostMapping("/unpause")
	public BaseRsp<Boolean> unpauseScene(@RequestBody SceneOpsReq sceneOpsReq) {

		String roomId = sceneOpsReq.getRoomId();
		Boolean ret = hldSceneService.unpauseScene(roomId);

		return new BaseRsp<>(ret);
	}

	@ApiOperation("查询流量")
	@GetMapping("flow")
	public BaseRsp<FlowRsp> getFlowList(
			@ApiParam(name = "room_id", value = "HLD房间id", required = true) String room_id) {

		List<Flow> flows = hldSceneService.getFlowList(room_id);
		FlowRsp ret = new FlowRsp();
		ret.setData(flows);

		return new BaseRsp<>(ret);
	}

	@ApiOperation("启动流量")
	@PostMapping("/flow")
	public BaseRsp startFlow(@RequestBody FlowOpsReq req) {

		String roomId = req.getRoomId();
		String userId = UserInfoContext.getUserContext().getUserId();

		// 1、鉴权
		hldRoomService.checkUserPermission(roomId, userId);

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
	public BaseRsp stopFlow(@RequestBody FlowOpsReq req) {

		String roomId = req.getRoomId();
		String userId = UserInfoContext.getUserContext().getUserId();
		// 1、鉴权
		hldRoomService.checkUserPermission(roomId, userId);
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

	@ApiOperation("重启节点")
	@PostMapping("/node/restart")
	public BaseRsp restartNode(@RequestBody SceneNodeOpsReq nodeOpsReq) {

		HldRoom room = hldRoomService.getById(nodeOpsReq.getResourceId());
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

		HldRoom room = hldRoomService.getById(nodeOpsReq.getResourceId());
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
}
