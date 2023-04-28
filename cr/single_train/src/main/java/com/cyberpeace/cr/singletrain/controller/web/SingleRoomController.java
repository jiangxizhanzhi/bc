package com.cyberpeace.cr.singletrain.controller.web;

import java.util.List;

import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.cr.crbase.controller.req.RoomDeviceApplyReq;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.service.CrRoomDeviceApplyService;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.controller.req.scene.SceneNodeOpsReq;
import com.cyberpeace.cr.crbase.service.context.SceneViewContext;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cr.singletrain.controller.req.SingleRoomOpsReq;
import com.cyberpeace.cr.singletrain.controller.req.SingleRoomTrainSubmitReq;
import com.cyberpeace.cr.singletrain.controller.req.SingleTrainListReq;
import com.cyberpeace.cr.singletrain.controller.req.SubmitMissionReq;
import com.cyberpeace.cr.singletrain.dao.entity.SingleRoom;
import com.cyberpeace.cr.singletrain.service.SingleRoomService;
import com.cyberpeace.cr.singletrain.service.bo.*;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.service.RemoteLogService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "单兵训练")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/single_train/room")
public class SingleRoomController {

	@Autowired
	private SingleRoomService singleRoomService;

	@Autowired
	private LocalSceneService localSceneService;

	@Autowired
	private RemoteLogService remoteLogService;

	@Autowired
	private CrRoomDeviceApplyService crRoomDeviceApplyService;

	@PostMapping
	@ApiOperation("创建")
	@RequiresPermissions(MenuConstant.WEB_CR_SINGLE_TRAIN)
	public BaseRsp<SingleRoomBo> create(@RequestBody @Validated SingleRoomOpsReq req) {

		SingleRoomBo singleRoomBo = singleRoomService.createRoom(req.getResourceId());

		// 记录远程操作日志
		remoteLogService.addCrLog(UserInfoContext.getUserContext(), OperationModule.SINGLE_TRAIN,
				OperationType.START, singleRoomBo.getEventData().getName());

		return new BaseRsp<>(singleRoomBo);
	}

	@GetMapping
	@ApiOperation("详情")
	@RequiresPermissions(MenuConstant.WEB_CR_SINGLE_TRAIN)
	public BaseRsp<SingleRoomBo> get(@RequestParam("resource_id") String businessTrainId) {
		return new BaseRsp<>(singleRoomService.getRoomInfo(businessTrainId));
	}

	@ApiOperation("单兵场景详情")
	@GetMapping("/scene")
	@RequiresPermissions(MenuConstant.WEB_CR_SINGLE_TRAIN)
	public BaseRsp<SceneViewContext> getScene(
			@ApiParam(name = "resource_id", value = "业务模板id", required = true) String resource_id) {
		SceneViewContext context = singleRoomService.viewScene(resource_id);

		return new BaseRsp<>(context);
	}

	@DeleteMapping
	@ApiOperation("关闭")
	@RequiresPermissions(MenuConstant.WEB_CR_SINGLE_TRAIN)
	public BaseRsp<Boolean> delete(@RequestBody @Validated SingleRoomOpsReq req) {

		Boolean deleteRoom = singleRoomService.deleteRoom(req.getResourceId());

		// 记录远程操作日志
		if (deleteRoom) {
			remoteLogService.addCrLog(UserInfoContext.getUserContext(),
					OperationModule.SINGLE_TRAIN, OperationType.STOP,
					singleRoomService.getRoomInfo(req.getResourceId()).getEventData().getName());
		}
		return new BaseRsp<>(deleteRoom);
	}

	@GetMapping("/list")
	@ApiOperation("分页查询")
	@RequiresPermissions(MenuConstant.WEB_CR_SINGLE_TRAIN)
	public BaseRsp<ListRsp<SingleRoomListBo>> list(SingleTrainListReq queryParams) {
		return new BaseRsp<>(singleRoomService.getSingleTrainPageList(queryParams));
	}

	@PostMapping("/mission_submit")
	@ApiOperation("任务提交")
	@RequiresPermissions(MenuConstant.WEB_CR_SINGLE_TRAIN)
	public BaseRsp<Boolean> submitMission(@RequestBody @Validated SubmitMissionReq requestData) {
		CrCommonValidator.checkStringExist(requestData.getAnswer(),
				CrBusinessErrorCode.ANSWER_NOT_NULLABLE);

		return new BaseRsp<>(
				singleRoomService.submitMission(requestData.getBusinessTemplateId(), requestData));
	}

	@GetMapping("/mission")
	@ApiOperation("任务获取")
	@RequiresPermissions(MenuConstant.WEB_CR_SINGLE_TRAIN)
	public BaseRsp<SingleMissionBo> mission(@RequestParam("resource_id") String businessTemplateId,
			@RequestParam("mission_id") String missionId) {
		return new BaseRsp<>(singleRoomService.getMission(businessTemplateId, missionId));
	}

	@GetMapping("/train_manual")
	@ApiOperation("训练手册")
	@RequiresPermissions(MenuConstant.WEB_CR_SINGLE_TRAIN)
	public BaseRsp<String> trainManual(@RequestParam("resource_id") String businessTemplateId) {
		return new BaseRsp<>(singleRoomService.getTrainManual(businessTemplateId));
	}

	@GetMapping("/train_record")
	@ApiOperation("训练记录")
	@RequiresPermissions(MenuConstant.WEB_CR_SINGLE_TRAIN)
	public BaseRsp<List<TrainRecordBo>>
			trainRecord(@RequestParam("resource_id") String businessTemplateId) {
		return new BaseRsp<>(singleRoomService.getTrainRecord(businessTemplateId));
	}

	@GetMapping("/train_record_markdown")
	@ApiOperation("训练记录-markdown")
	@RequiresPermissions(MenuConstant.WEB_CR_SINGLE_TRAIN)
	public BaseRsp<String> trainRecordMd(@RequestParam("resource_id") String businessTemplateId) {
		return new BaseRsp<>(singleRoomService.getTrainRecordMarkdown(businessTemplateId));
	}

	@GetMapping("/time_line")
	@ApiOperation("训练时间线")
	@RequiresPermissions(MenuConstant.WEB_CR_SINGLE_TRAIN)
	public BaseRsp<TrainTimeLineBo>
			trainTimeLine(@RequestParam("resource_id") String businessTemplateId) {
		return new BaseRsp<>(singleRoomService.getTimeLineData(businessTemplateId));
	}

	@ApiOperation("重启节点")
	@PostMapping("/scene/node/restart")
	@RequiresPermissions(MenuConstant.WEB_CR_SINGLE_TRAIN)
	public BaseRsp restartNode(@RequestBody SceneNodeOpsReq nodeOpsReq) {
		String userId = UserInfoContext.getUserContext().getUserId();
		SingleRoom room = singleRoomService
				.getByBusinessTemplateIdAndUserId(nodeOpsReq.getResourceId(), userId);
		if (room == null) {
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
	@PostMapping("/scene/node/recreate")
	@RequiresPermissions(MenuConstant.WEB_CR_SINGLE_TRAIN)
	public BaseRsp recreateNode(@RequestBody SceneNodeOpsReq nodeOpsReq) {
		String userId = UserInfoContext.getUserContext().getUserId();
		SingleRoom room = singleRoomService
				.getByBusinessTemplateIdAndUserId(nodeOpsReq.getResourceId(), userId);
		if (room == null) {
			throw new BusinessException(CrBusinessErrorCode.SCENE_NOT_STARTED);
		}
		Boolean result = localSceneService.recreateNode(room.getLocalSceneId(),
				nodeOpsReq.getNodeId());
		if (!result) {
			throw new BusinessException(CrBusinessErrorCode.FAILED_TO_RECREATE_NODE);
		}
		return new BaseRsp();
	}

	@ApiOperation("训练历史")
	@GetMapping("/train_history")
	@RequiresPermissions(MenuConstant.WEB_CR_SINGLE_TRAIN)
	public BaseRsp<List<TrainHistoryBo>>
			trainHistory(@RequestParam("resource_id") String businessTemplateId) {
		return new BaseRsp<>(singleRoomService.getTrainHistory(businessTemplateId));
	}

	@ApiOperation("训练提交")
	@PostMapping("/train_submit")
	@RequiresPermissions(MenuConstant.WEB_CR_SINGLE_TRAIN)
	public BaseRsp<String>
			submitTrain(@RequestBody @Validated SingleRoomTrainSubmitReq requestBody) {
		return new BaseRsp<>(singleRoomService.submitTrain(requestBody.getResourceId()));
	}

	@ApiOperation("实装分配-设备审批申请")
	@PostMapping("/device/apply")
	@RequiresPermissions(MenuConstant.WEB_CR_SINGLE_TRAIN)
	public BaseRsp<Boolean> deviceApply(@RequestBody @Validated RoomDeviceApplyReq roomDeviceApplyReq) {
		// `roomDeviceApplyReq.getRoomId()`实际获取到的是businessTemplateId
		SingleRoomBo singleRoomBo = singleRoomService.getRoomInfo(roomDeviceApplyReq.getRoomId());
		if (singleRoomBo == null) {
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}

		return new BaseRsp<>(crRoomDeviceApplyService.apply(
				singleRoomBo.getRoomInfo().getResourceId(),
				singleRoomBo.getEventData().getName(),
				roomDeviceApplyReq.getDeviceId(),
				singleRoomBo.getLocalSceneId(),
				BusinessTemplate.BusinessType.TRAINING_SINGLE.getCode(),
				singleRoomBo.getResourceId()));
	}

}
