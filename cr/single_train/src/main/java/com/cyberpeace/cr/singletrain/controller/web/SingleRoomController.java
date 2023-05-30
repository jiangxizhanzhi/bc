package com.cyberpeace.cr.singletrain.controller.web;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.controller.req.scene.SceneNodeOpsReq;
import com.cyberpeace.cr.crbase.service.context.SceneViewContext;
import com.cyberpeace.cr.crbase.service.context.device.AssetPageReq;
import com.cyberpeace.cr.crbase.service.context.manual.ManualStageBo;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cr.singletrain.controller.req.SingleRoomOpsReq;
import com.cyberpeace.cr.singletrain.controller.req.SingleRoomTrainSubmitReq;
import com.cyberpeace.cr.singletrain.controller.req.SingleTrainListReq;
import com.cyberpeace.cr.singletrain.controller.req.SubmitMissionReq;
import com.cyberpeace.cr.singletrain.dao.entity.SingleRoom;
import com.cyberpeace.cr.singletrain.service.SingleRoomService;
import com.cyberpeace.cr.singletrain.service.SingleTemplateService;
import com.cyberpeace.cr.singletrain.service.bo.*;
import com.cyberpeace.cr.singletrain.service.bo.mission.SingleMatrixProgressRsp;
import com.cyberpeace.cr.singletrain.service.bo.record.TrainingRecordListBo;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.cs.service.context.IdNameType;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.service.RemoteLogService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
@Slf4j
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
	private SingleTemplateService singleTemplateService;

	@Autowired
	private RemoteLogService remoteLogService;

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
	@ApiOperation("训练手册, 需要开启单兵训练")
	@RequiresPermissions(MenuConstant.WEB_CR_SINGLE_TRAIN)
	public BaseRsp<List<ManualStageBo>>
			trainManual(@RequestParam("resource_id") String businessTemplateId) {
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
			log.error("scene not started: ResourceId = [{}]", nodeOpsReq.getResourceId());
			throw new BusinessException(CrBusinessErrorCode.SCENE_NOT_STARTED);
		}
		Boolean result = localSceneService.restartNode(room.getLocalSceneId(),
				nodeOpsReq.getNodeId());
		if (!result) {
			log.error("failed_to_restart_node: LocalSceneId = [{}]", room.getLocalSceneId());
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
			log.error("scene not started: ResourceId = [{}]", nodeOpsReq.getResourceId());
			throw new BusinessException(CrBusinessErrorCode.SCENE_NOT_STARTED);
		}
		Boolean result = localSceneService.recreateNode(room.getLocalSceneId(),
				nodeOpsReq.getNodeId());
		if (!result) {
			log.error("failed_to_restart_node: LocalSceneId = [{}]", room.getLocalSceneId());
			throw new BusinessException(CrBusinessErrorCode.FAILED_TO_RECREATE_NODE);
		}
		return new BaseRsp();
	}

	@ApiOperation("训练历史")
	@GetMapping("/train_history")
	@RequiresPermissions(MenuConstant.WEB_CR_SINGLE_TRAIN)
	public BaseRsp<List<TrainingRecordListBo>>
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

	@ApiOperation("获取任务矩阵和训练进度")
	@GetMapping("/mission/matrix")
	@RequiresPermissions(MenuConstant.WEB_CR_SINGLE_TRAIN)
	public BaseRsp<SingleMatrixProgressRsp> getMissionMatrix(
			@ApiParam(name = "resource_id", value = "单兵模板id", required = true) String resource_id) {
		if (StringUtils.isBlank(resource_id)) {
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}
		SingleMatrixProgressRsp res = singleRoomService.getMatrixRsp(resource_id);
		return new BaseRsp<>(res);
	}

	/**
	 * 获取资产清单
	 * 透传中台节点数据
	 *
	 * @param req 请求体
	 * @return {@code BaseRsp<ListRsp<JSONObject>>}
	 */
	@ApiOperation("资产清单, 需要开启场景")
	@GetMapping("/asset/list")
	@RequiresPermissions(MenuConstant.WEB_CR_SINGLE_TRAIN)
	public BaseRsp<ListRsp<JSONObject>> getAssets(@Validated AssetPageReq req) {
		CrCommonValidator.validateId(req.getResourceId());
		ListRsp<JSONObject> res = singleRoomService.getAssetList(req);
		return new BaseRsp<>(res);
	}

	/**
	 * 获取操作系统名字
	 * 透传中台节点数据
	 *
	 * @return {@code BaseRsp<List<IdNameType>>}
	 */
	@ApiOperation("操作系统列表, 需要开启场景")
	@GetMapping("/os/list")
	@RequiresPermissions(MenuConstant.WEB_CR_SINGLE_TRAIN)
	public BaseRsp<List<IdNameType>> getOsNames(
			@ApiParam(name = "resource_id", value = "单兵训练id", required = true) String resource_id) {
		CrCommonValidator.validateId(resource_id);
		List<IdNameType> res = singleRoomService.getOsNames(resource_id);
		return new BaseRsp<>(res);
	}
}
