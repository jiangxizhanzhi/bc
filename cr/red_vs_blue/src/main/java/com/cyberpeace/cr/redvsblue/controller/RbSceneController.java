package com.cyberpeace.cr.redvsblue.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.cyberpeace.common.constant.ErrorCode;
import com.cyberpeace.common.model.user.UserInfo;
import com.cyberpeace.cr.crbase.service.CrSceneService;
import com.cyberpeace.cr.crbase.service.context.device.AssetPageReq;
import com.cyberpeace.cr.redvsblue.controller.req.RbCommonDeviceReq;
import com.cyberpeace.integratation.service.RemoteUserService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.controller.req.FlowOpsReq;
import com.cyberpeace.cr.crbase.controller.req.scene.SceneNodeOpsReq;
import com.cyberpeace.cr.crbase.controller.req.scene.SceneOpsReq;
import com.cyberpeace.cr.crbase.controller.rsp.StartSceneRsp;
import com.cyberpeace.cr.crbase.service.context.scene.Flow;
import com.cyberpeace.cr.crbase.service.context.scene.FlowRsp;
import com.cyberpeace.cr.redvsblue.dao.entity.RbCommonDevice;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.service.RbCommonDeviceService;
import com.cyberpeace.cr.redvsblue.service.RbDeviceService;
import com.cyberpeace.cr.redvsblue.service.RbRoomService;
import com.cyberpeace.cr.redvsblue.service.RbSceneService;
import com.cyberpeace.cr.redvsblue.service.context.RbCommonDeviceContext;
import com.cyberpeace.cr.redvsblue.service.context.RbDeviceContext;
import com.cyberpeace.cr.redvsblue.service.context.RbSceneViewContext;
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
	private RbDeviceService rbDeviceService;

	@Autowired
	private RbCommonDeviceService rbCommonDeviceService;

	@Autowired
	private CrSceneService crSceneService;

	@Autowired
	private RemoteUserService remoteUserService;

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
	public BaseRsp<RbSceneViewContext> getScene(
			@ApiParam(name = "room_id", value = "红蓝对抗实例id", required = true) String room_id,
			@ApiParam(name = "role_id", value = "角色id", required = false) String role_id) {
		RbSceneViewContext context = rbSceneService.viewScene(room_id, role_id);

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
			log.error("scene is not started, room id is {}", roomId);
			throw new BusinessException(CrBusinessErrorCode.SCENE_NOT_STARTED);
		}
		// 3、开启流量
		Boolean result = localSceneService.startFlow(localScene, req.getNodeId(), req.getFlowId());
		if (!result) {
			log.error("start flow failed, room id is {}, flow id is {}", roomId, req.getFlowId());
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
			log.error("scene is not started, room id is {}", roomId);
			throw new BusinessException(CrBusinessErrorCode.SCENE_NOT_STARTED);
		}
		// 3、停止流量
		Boolean result = localSceneService.stopFlow(localScene, req.getNodeId(), req.getFlowId());
		if (!result) {
			log.error("stop flow failed, room id is {}, flow id is {}", roomId, req.getFlowId());
			throw new BusinessException(CrBusinessErrorCode.STOP_FLOW_FAILURE);
		}
		return new BaseRsp();
	}

	@ApiOperation("重启节点")
	@PostMapping("/node/restart")
	public BaseRsp restartNode(@RequestBody SceneNodeOpsReq nodeOpsReq) {
		RbRoom room = rbRoomService.getById(nodeOpsReq.getResourceId());
		if (room == null || StringUtils.isBlank(room.getLocalSceneId())) {
			log.error("scene is not started, room id is {}", nodeOpsReq.getResourceId());
			throw new BusinessException(CrBusinessErrorCode.SCENE_NOT_STARTED);
		}
		Boolean result = localSceneService.restartNode(room.getLocalSceneId(),
				nodeOpsReq.getNodeId());
		if (!result) {
			log.error("restart node failed, room id is {}, node id is {}",
					nodeOpsReq.getResourceId(), nodeOpsReq.getNodeId());
			throw new BusinessException(CrBusinessErrorCode.FAILED_TO_RESTART_NODE);
		}
		return new BaseRsp();
	}

	@ApiOperation("重建节点")
	@PostMapping("/node/recreate")
	public BaseRsp recreateNode(@RequestBody SceneNodeOpsReq nodeOpsReq) {
		RbRoom room = rbRoomService.getById(nodeOpsReq.getResourceId());
		if (room == null || StringUtils.isBlank(room.getLocalSceneId())) {
			log.error("scene is not started, room id is {}", nodeOpsReq.getResourceId());
			throw new BusinessException(CrBusinessErrorCode.SCENE_NOT_STARTED);
		}
		Boolean result = localSceneService.recreateNode(room.getLocalSceneId(),
				nodeOpsReq.getNodeId());
		if (!result) {
			log.error("recreate node failed, room id is {}, node id is {}",
					nodeOpsReq.getResourceId(), nodeOpsReq.getNodeId());
			throw new BusinessException(CrBusinessErrorCode.FAILED_TO_RECREATE_NODE);
		}
		return new BaseRsp();
	}

	@ApiOperation("创建启动节点")
	@PostMapping("/node/create")
	public BaseRsp createNode(@RequestBody SceneNodeOpsReq nodeOpsReq) {
		RbRoom room = rbRoomService.getById(nodeOpsReq.getResourceId());
		if (room == null || StringUtils.isBlank(room.getLocalSceneId())) {
			log.error("scene is not started, room id is {}", nodeOpsReq.getResourceId());
			throw new BusinessException(CrBusinessErrorCode.SCENE_NOT_STARTED);
		}
		Boolean result = localSceneService.createNode(room.getLocalSceneId(),
				nodeOpsReq.getNodeId());
		if (!result) {
			log.error("create node failed, room id is {}, node id is {}",
					nodeOpsReq.getResourceId(), nodeOpsReq.getNodeId());
			throw new BusinessException(CrBusinessErrorCode.FAILED_TO_CREATE_NODE);
		}
		return new BaseRsp();
	}

	/**
	 * 获取设备统计数据
	 * 透传中台数据
	 *
	 * @param resource_id 资源id
	 * @return {@code BaseRsp<JSONObject>}
	 */
	@ApiOperation("设备统计")
	@GetMapping("/device/count")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<JSONObject> getDeviceStatistics(
			@ApiParam(name = "resource_id", value = "红蓝房间id", required = true) String resource_id) {
		if (StringUtils.isBlank(resource_id)) {
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}
		JSONObject res = rbSceneService.getNodeStatistics(resource_id);
		return new BaseRsp<>(res);
	}

	/**
	 * 获取设备列表
	 * 透传中台
	 *
	 * @param resource_id 资源id
	 * @param category    类别
	 * @param name        名称
	 * @return {@code BaseRsp<JSONArray>}
	 */
	@ApiOperation("设备清单")
	@GetMapping("/device/list")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<JSONArray> getDeviceList(
			@ApiParam(name = "resource_id", value = "红蓝房间id", required = true) String resource_id,
			@ApiParam(name = "category", value = "设备类型", required = false) Integer category,
			@ApiParam(name = "name", value = "搜索关键字", required = false) String name) {
		if (StringUtils.isBlank(resource_id)) {
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}
		JSONArray res = rbSceneService.getAssetList(resource_id, category, name);
		return new BaseRsp<>(res);
	}

	@ApiOperation("常用资产可添加列表")
	@GetMapping("/device/common_device/not_yet")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<List<RbCommonDeviceContext>> getNotYetCommonDevices(
			@ApiParam(name = "resource_id", value = "红蓝房间id", required = true) String resource_id,
			@ApiParam(name = "category", value = "设备类型", required = false) Integer category,
			@ApiParam(name = "name", value = "搜索关键字", required = false) String name) {

		// 1.拉取场景中可用 设备
		AssetPageReq assetPageReq = new AssetPageReq();
		assetPageReq.setResource_id(resource_id);
		List<JSONObject> availableDevices = null;
		try {
			availableDevices = crSceneService.getAssetList(assetPageReq, true);
		} catch (BusinessException e) {
			if(StringUtils.isNotEmpty(e.getMessage())
					&& e.getMessage().equals(ErrorCode.UNKNOWN_SCENE)) {
				log.error("localscene: [{}], is not started", resource_id);
				throw new BusinessException(ErrorCode.NOT_STARTED_SCENE);
			}
		}
		// 2.获取占用的设备
		List<RbDeviceContext> occupyDevices = rbDeviceService.getByRoomIdAndOptionalDevice(resource_id, null);
		// 3.查询已添加设备
		List<RbCommonDevice> allCollectedCommonDeviceDevices = rbCommonDeviceService.getAllCollectedCommonDeviceDevices();

		// 4.装配设备信息和设置占用标志 并过滤 已添加设备
		List<RbCommonDeviceContext> commonDeviceContexts = combineAvailableDevices(availableDevices, occupyDevices, resource_id, allCollectedCommonDeviceDevices);

		return new BaseRsp<>(commonDeviceContexts);
	}

	@ApiOperation("添加常用资产")
	@PostMapping("/device/common_device")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp addDevice(@RequestBody RbCommonDeviceReq rbCommonDeviceReq) {
		if(StringUtils.isEmpty(rbCommonDeviceReq.getRoomId())) {
			throw new BusinessException(CrBusinessErrorCode.ROOM_NOT_EXIST);
		}
		List<RbCommonDeviceContext> commonDeviceContextList = rbCommonDeviceReq.getCommonDeviceContextList();
		if(CollectionUtils.isEmpty(commonDeviceContextList)) {
			throw new BusinessException(CrBusinessErrorCode.INVALID_COMMON_DEVICE);
		}
		commonDeviceContextList.forEach(item -> {item.setRoomId(rbCommonDeviceReq.getRoomId());});
		rbCommonDeviceService.addListToCollect(commonDeviceContextList);
		return new BaseRsp();
	}

	@ApiOperation("删除常用资产")
	@DeleteMapping("/device/common_device")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp delDevice(@RequestBody RbCommonDeviceContext rbCommonDeviceContext) {
		String resourceId = rbCommonDeviceContext.getResourceId();
		rbCommonDeviceService.delCollect(resourceId);
		return new BaseRsp();
	}


	@ApiOperation("常用资产已添加列表")
	@GetMapping("/device/common_device/already")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<List<RbCommonDeviceContext>> getAlreadyCommonDevices(
			@ApiParam(name = "resource_id", value = "红蓝房间id", required = true) String resource_id,
			@ApiParam(name = "category", value = "设备类型", required = false) Integer category,
			@ApiParam(name = "name", value = "搜索关键字", required = false) String name) {
		// 2.拉取场景中可用 设备
		AssetPageReq assetPageReq = new AssetPageReq();
		assetPageReq.setResource_id(resource_id);
		List<JSONObject> availableDevices = null;
		try {
			availableDevices = crSceneService.getAssetList(assetPageReq, true);
		} catch (BusinessException e) {
			if(StringUtils.isNotEmpty(e.getMessage())
					&& e.getMessage().equals(ErrorCode.UNKNOWN_SCENE)) { // 如果是场景未启动导致的就返回空数组
				return new BaseRsp<>(new ArrayList<>());
			}
		}

		// 3.查询已添加的设备
		String userId = UserInfoContext.getUserContext().getUserId();
		List<RbCommonDevice> collectedCommonDevices = rbCommonDeviceService.getCollectedCommonDevices(resource_id, userId);

		// 4.装配设备信息
		List<RbCommonDeviceContext> commonDeviceContexts = combineAvailableDevices(availableDevices, collectedCommonDevices);

		return new BaseRsp<>(commonDeviceContexts);
	}

	/**
	 * 根据 场景中可用设备和已占用设备 装配常用资产列表
	 * @param availableDevices 场景中的可用设备，中台给的设备状态信息
	 * @param occupyDevices 已占用设备列表
	 * @param roomId 红蓝房间id
	 * @param allCollectedCommonDeviceDevices 已添加常用资产列表
	 * @return 可添加常用资产列表
	 */
	private List<RbCommonDeviceContext> combineAvailableDevices(List<JSONObject> availableDevices,
																List<RbDeviceContext> occupyDevices,
																String roomId,
																List<RbCommonDevice> allCollectedCommonDeviceDevices) {
		if(CollectionUtils.isEmpty(availableDevices)) {
			return new ArrayList<>();
		}

		List<String> userIds = allCollectedCommonDeviceDevices.stream()
				.map(RbCommonDevice::getCollectUserId).collect(Collectors.toList());
		List<UserInfo> userInfos = remoteUserService.getByIdList(userIds);
		List<RbCommonDeviceContext> commonDeviceContexts = new ArrayList<>();
		for(int i = 0; i < availableDevices.size(); i ++) {
			JSONObject availableDevice = availableDevices.get(i);

			RbCommonDeviceContext context = new RbCommonDeviceContext();
			for(RbCommonDevice collectedCommonDeviceDevice : allCollectedCommonDeviceDevices) {
				if(!collectedCommonDeviceDevice.getDeviceId().equals(availableDevice.getString("id"))) {
					continue;
				}
				context.setOccupyFlag(1);
				UserInfo userInfo = remoteUserService.searchUserByUserIdInUserList(collectedCommonDeviceDevice.getCollectUserId(), userInfos);
				if(userInfo == null) {
					continue;
				}
				context.setCollectUserId(userInfo.getUserId());
				context.setCollectUser(userInfo.getUserName());
			}

			for(RbDeviceContext occupyDevice : occupyDevices) {
				if(!occupyDevice.getDeviceId().equals(availableDevice.getString("id"))) {
					context.setOccupyFlag(0);
				} else {
					context.setOccupyFlag(1);
					context.setCollectUserId(occupyDevice.getCreateUserId());
					context.setCollectUser(occupyDevice.getCreateUser());
				}
			}
			context.setDeviceId(availableDevice.getString("id"));
			context.setDeviceInfo(availableDevice);
			context.setRoomId(roomId);
			commonDeviceContexts.add(context);
		}
		return commonDeviceContexts;
	}

	private List<RbCommonDeviceContext> combineAvailableDevices(List<JSONObject> availableDevices,
																List<RbCommonDevice> collectedCommonDevices) {
		if(CollectionUtils.isEmpty(availableDevices) || CollectionUtils.isEmpty(collectedCommonDevices)) {
			return new ArrayList<>();
		}
		List<RbCommonDeviceContext> commonDeviceContexts = new ArrayList<>();
		outer:
		for(int i = 0; i < availableDevices.size(); i ++) {
			JSONObject availableDevice = availableDevices.get(i);
			RbCommonDeviceContext context = new RbCommonDeviceContext();

			for(RbCommonDevice commonDevice : collectedCommonDevices) {
				if(!commonDevice.getDeviceId().equals(availableDevice.getString("id"))) {
					continue;
				}
				BeanUtils.copyProperties(commonDevice, context);
				context.setDeviceInfo(availableDevice);
				context.setOccupyFlag(1);
				commonDeviceContexts.add(context);
				continue outer;
			}
		}
		return commonDeviceContexts;
	}
}
