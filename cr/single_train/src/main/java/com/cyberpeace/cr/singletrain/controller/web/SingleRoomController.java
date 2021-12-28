package com.cyberpeace.cr.singletrain.controller.web;

import java.util.List;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
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
import com.cyberpeace.cr.singletrain.controller.req.SingleRoomOpsReq;
import com.cyberpeace.cr.singletrain.controller.req.SingleTrainListReq;
import com.cyberpeace.cr.singletrain.controller.req.SubmitMissionReq;
import com.cyberpeace.cr.singletrain.dao.entity.SingleRoom;
import com.cyberpeace.cr.singletrain.service.SingleRoomService;
import com.cyberpeace.cr.singletrain.service.bo.*;
import com.cyberpeace.cs.service.LocalSceneService;

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

	@PostMapping
	@ApiOperation("创建")
	public BaseRsp<SingleRoomBo> create(@RequestBody @Validated SingleRoomOpsReq req) {
		return new BaseRsp<>(singleRoomService.createRoom(req.getResourceId()));
	}

	@GetMapping
	@ApiOperation("详情")
	public BaseRsp<SingleRoomBo> get(@RequestParam("resource_id") String businessTrainId) {
		return new BaseRsp<>(singleRoomService.getRoomInfo(businessTrainId));
	}

	@ApiOperation("单兵场景详情")
	@GetMapping("/scene")
	public BaseRsp<SceneViewContext> getScene(
			@ApiParam(name = "resource_id", value = "业务模板id", required = true) String resource_id) {
		SceneViewContext context = singleRoomService.viewScene(resource_id);

		return new BaseRsp<>(context);
	}

	@DeleteMapping
	@ApiOperation("关闭")
	public BaseRsp<Boolean> delete(@RequestBody @Validated SingleRoomOpsReq req) {
		return new BaseRsp<>(singleRoomService.deleteRoom(req.getResourceId()));
	}

	@GetMapping("/list")
	@ApiOperation("分页查询")
	public BaseRsp<ListRsp<SingleRoomListBo>> list(SingleTrainListReq queryParams) {
		return new BaseRsp<>(singleRoomService.getSingleTrainPageList(queryParams));
	}

	@PostMapping("/mission_submit")
	@ApiOperation("任务提交")
	public BaseRsp<Boolean> submitMission(@RequestBody @Validated SubmitMissionReq requestData) {
		return new BaseRsp<>(
				singleRoomService.submitMission(requestData.getBusinessTemplateId(), requestData));
	}

	@GetMapping("/mission")
	@ApiOperation("任务获取")
	public BaseRsp<SingleMissionBo> mission(@RequestParam("resource_id") String businessTemplateId,
			@RequestParam("mission_id") String missionId) {
		return new BaseRsp<>(singleRoomService.getMission(businessTemplateId, missionId));
	}

	@GetMapping("/train_manual")
	@ApiOperation("训练手册")
	public BaseRsp<String> trainManual(@RequestParam("resource_id") String businessTemplateId) {
		return new BaseRsp<>(singleRoomService.getTrainManual(businessTemplateId));
	}

	@GetMapping("/train_record")
	@ApiOperation("训练记录")
	public BaseRsp<List<TrainRecordBo>>
			trainRecord(@RequestParam("resource_id") String businessTemplateId) {
		return new BaseRsp<>(singleRoomService.getTrainRecord(businessTemplateId));
	}

	@GetMapping("/train_record_markdown")
	@ApiOperation("训练记录-markdown")
	public BaseRsp<String> trainRecordMd(@RequestParam("resource_id") String businessTemplateId) {
		return new BaseRsp<>(singleRoomService.getTrainRecordMarkdown(businessTemplateId));
	}

	@GetMapping("/time_line")
	@ApiOperation("训练时间线")
	public BaseRsp<TrainTimeLineBo>
			trainTimeLine(@RequestParam("resource_id") String businessTemplateId) {
		return new BaseRsp<>(singleRoomService.getTimeLineData(businessTemplateId));
	}

	@ApiOperation("重启节点")
	@PostMapping("/scene/node/restart")
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
}
