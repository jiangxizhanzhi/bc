package com.cyberpeace.cr.singletrain.controller.web;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.annoations.BehaviorRecord;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.enums.BehaviorModuleEnum;
import com.cyberpeace.common.enums.BehaviorTypeEnum;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.ListRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.controller.req.scene.SceneNodeOpsReq;
import com.cyberpeace.cr.crbase.service.context.SceneViewContext;
import com.cyberpeace.cr.crbase.service.context.device.AssetPageReq;
import com.cyberpeace.cr.crbase.service.context.manual.ManualStageBo;
import com.cyberpeace.cr.crbase.service.context.mission.TimeLineData;
import com.cyberpeace.cr.crbase.utils.CrCommonValidator;
import com.cyberpeace.cr.singletrain.controller.req.SingleRoomOpsReq;
import com.cyberpeace.cr.singletrain.controller.req.SingleRoomTrainSubmitReq;
import com.cyberpeace.cr.singletrain.controller.req.SingleTrainListReq;
import com.cyberpeace.cr.singletrain.controller.req.SubmitMissionReq;
import com.cyberpeace.cr.singletrain.dao.entity.SingleRoom;
import com.cyberpeace.cr.singletrain.service.SingleRoomService;
import com.cyberpeace.cr.singletrain.service.bo.SingleMissionBo;
import com.cyberpeace.cr.singletrain.service.bo.SingleRoomBo;
import com.cyberpeace.cr.singletrain.service.bo.SingleRoomListBo;
import com.cyberpeace.cr.singletrain.service.bo.TrainRecordBo;
import com.cyberpeace.cr.singletrain.service.bo.mission.SingleMatrixProgressRsp;
import com.cyberpeace.cr.singletrain.service.bo.record.TrainingRecordListBo;
import com.cyberpeace.cs.service.LocalSceneService;
import com.cyberpeace.cs.service.context.IdNameType;
import com.cyberpeace.integratation.constant.OperationModule;
import com.cyberpeace.integratation.constant.OperationType;
import com.cyberpeace.integratation.model.scene.TopoDigest;
import com.cyberpeace.integratation.service.RemoteLogService;
import com.cyberpeace.integratation.service.RemoteTopoService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

/**
 * 单房间Controller
 *
 * @author SN
 * @date 2024/11/14
 */
@Slf4j
@Api(tags = "单兵训练")
@RestController
@RequiresAuthentication
@RequestMapping("/cr/single_train/room")
public class SingleRoomController {

	/**
	 * 单房间Service
	 */
	@Autowired
	private SingleRoomService singleRoomService;

	/**
	 * 当地场景Service
	 */
	@Autowired
	private LocalSceneService localSceneService;

	/**
	 * 远程日志Service
	 */
	@Autowired
	private RemoteLogService remoteLogService;

	/**
	 * 远程拓扑Service
	 */
	@Autowired
	private RemoteTopoService remoteTopoService;

	/**
	 * 创建
	 *
	 * @param req 请求体
	 * @return {@code BaseRsp<SingleRoomBo> }
	 */
	@PostMapping
	@ApiOperation("创建")
	@RequiresPermissions(MenuConstant.WEB_CR_SINGLE_TRAIN)
	@BehaviorRecord(module = BehaviorModuleEnum.SINGLE_TRAIN, action = BehaviorTypeEnum.START_SCENE, name="eventData.name")
	public BaseRsp<SingleRoomBo> create(@RequestBody @Validated SingleRoomOpsReq req) {

		SingleRoomBo singleRoomBo = singleRoomService.createRoom(req.getResourceId());

		// 记录远程操作日志
		remoteLogService.addCrLog(UserInfoContext.getUserContext(), OperationModule.SINGLE_TRAIN,
				OperationType.START, singleRoomBo.getEventData().getName());

		return new BaseRsp<>(singleRoomBo);
	}

	/**
	 * 获取
	 *
	 * @param businessTrainId 业务训练id
	 * @return {@code BaseRsp<SingleRoomBo> }
	 */
	@GetMapping
	@ApiOperation("详情")
	@RequiresPermissions(MenuConstant.WEB_CR_SINGLE_TRAIN)
	@BehaviorRecord(module = BehaviorModuleEnum.SINGLE_TRAIN, action = BehaviorTypeEnum.ACCESS, name="eventData.name")
	public BaseRsp<SingleRoomBo> get(@RequestParam("resource_id") String businessTrainId) {
		SingleRoomBo roomInfo = singleRoomService.getRoomInfo(businessTrainId);

		if(roomInfo != null && roomInfo.getEventData() != null) {
			// 训练场景拓扑节点数量获取
			String copyedTopoId = roomInfo.getCopiedTopoId();
			List<String> copyedTopoIds = new ArrayList<>();
			copyedTopoIds.add(copyedTopoId);
			// 训练场景拓扑节点数量获取
			Map<String, TopoDigest> topologyNodesStats = remoteTopoService
					.batchGetTopoDigest(copyedTopoIds);

			roomInfo.getEventData().setNodesCount(topologyNodesStats.get(copyedTopoId).getNodeCounts());
		}
		return new BaseRsp<>(roomInfo);
	}

	/**
	 * 获取场景
	 *
	 * @param resourceId 资源id
	 * @return {@code BaseRsp<SceneViewContext> }
	 */
	@ApiOperation("单兵场景详情")
	@GetMapping("/scene")
	@RequiresPermissions(MenuConstant.WEB_CR_SINGLE_TRAIN)
	public BaseRsp<SceneViewContext> getScene(
			@ApiParam(name = "resource_id", value = "业务模板id", required = true) @RequestParam(
					value = "resource_id", required = true) String resourceId) {
		SceneViewContext context = singleRoomService.viewScene(resourceId);

		return new BaseRsp<>(context);
	}

	/**
	 * 删除
	 *
	 * @param req 请求体
	 * @return {@code BaseRsp<Boolean> }
	 */
	@DeleteMapping
	@ApiOperation("关闭")
	@RequiresPermissions(MenuConstant.WEB_CR_SINGLE_TRAIN)
	public BaseRsp<Boolean> delete(@RequestBody @Validated SingleRoomOpsReq req) {

		Boolean deleteRoom = singleRoomService.deleteRoom(req.getResourceId());

		// 记录远程操作日志
		if (Boolean.TRUE.equals(deleteRoom)) {
			remoteLogService.addCrLog(UserInfoContext.getUserContext(),
					OperationModule.SINGLE_TRAIN, OperationType.STOP,
					singleRoomService.getRoomInfo(req.getResourceId()).getEventData().getName());
		}
		return new BaseRsp<>(deleteRoom);
	}

	/**
	 * 列表
	 *
	 * @param queryParams 查询参数
	 * @return {@code BaseRsp<ListRsp<SingleRoomListBo>> }
	 */
	@GetMapping("/list")
	@ApiOperation("分页查询")
	@RequiresPermissions(MenuConstant.WEB_CR_SINGLE_TRAIN)
	public BaseRsp<ListRsp<SingleRoomListBo>> list(SingleTrainListReq queryParams) {
		return new BaseRsp<>(singleRoomService.getSingleTrainPageList(queryParams));
	}

	/**
	 * 提交任务
	 *
	 * @param requestData 请求数据
	 * @return {@code BaseRsp<Boolean> }
	 */
	@PostMapping("/mission_submit")
	@ApiOperation("任务提交")
	@RequiresPermissions(MenuConstant.WEB_CR_SINGLE_TRAIN)
	public BaseRsp<Boolean> submitMission(@RequestBody @Validated SubmitMissionReq requestData) {
		CrCommonValidator.checkStringExist(requestData.getAnswer(),
				CrBusinessErrorCode.ANSWER_NOT_NULLABLE);

		return new BaseRsp<>(
				singleRoomService.submitMission(requestData.getBusinessTemplateId(), requestData));
	}

	/**
	 * 任务
	 *
	 * @param businessTemplateId 业务模板id
	 * @param missionId          任务id
	 * @return {@code BaseRsp<SingleMissionBo> }
	 */
	@GetMapping("/mission")
	@ApiOperation("任务获取")
	@RequiresPermissions(MenuConstant.WEB_CR_SINGLE_TRAIN)
	public BaseRsp<SingleMissionBo> mission(@RequestParam("resource_id") String businessTemplateId,
			@RequestParam("mission_id") String missionId) {
		return new BaseRsp<>(singleRoomService.getMission(businessTemplateId, missionId));
	}

	/**
	 * 训练人工
	 *
	 * @param businessTemplateId 业务模板id
	 * @return {@code BaseRsp<List<ManualStageBo>> }
	 */
	@GetMapping("/train_manual")
	@ApiOperation("训练手册, 需要开启单兵训练")
	@RequiresPermissions(MenuConstant.WEB_CR_SINGLE_TRAIN)
	public BaseRsp<List<ManualStageBo>>
			trainManual(@RequestParam("resource_id") String businessTemplateId) {
		return new BaseRsp<>(singleRoomService.getTrainManual(businessTemplateId));
	}

	/**
	 * 训练记录
	 *
	 * @param businessTemplateId 业务模板id
	 * @return {@code BaseRsp<List<TrainRecordBo>> }
	 */
	@GetMapping("/train_record")
	@ApiOperation("训练记录")
	@RequiresPermissions(MenuConstant.WEB_CR_SINGLE_TRAIN)
	public BaseRsp<List<TrainRecordBo>>
			trainRecord(@RequestParam("resource_id") String businessTemplateId) {
		return new BaseRsp<>(singleRoomService.getTrainRecord(businessTemplateId));
	}

	/**
	 * 训练记录markdown
	 *
	 * @param businessTemplateId 业务模板id
	 * @return {@code BaseRsp<String> }
	 */
	@GetMapping("/train_record_markdown")
	@ApiOperation("训练记录-markdown")
	@RequiresPermissions(MenuConstant.WEB_CR_SINGLE_TRAIN)
	public BaseRsp<String> trainRecordMd(@RequestParam("resource_id") String businessTemplateId) {
		return new BaseRsp<>(singleRoomService.getTrainRecordMarkdown(businessTemplateId));
	}

	/**
	 * 训练时间线
	 *
	 * @param businessTemplateId 业务模板id
	 * @return {@code BaseRsp<TimeLineData> }
	 */
	@GetMapping("/time_line")
	@ApiOperation("训练时间线")
	@RequiresPermissions(MenuConstant.WEB_CR_SINGLE_TRAIN)
	public BaseRsp<TimeLineData>
			trainTimeLine(@RequestParam("resource_id") String businessTemplateId) {
		return new BaseRsp<>(singleRoomService.getTimeLineData(businessTemplateId));
	}

	/**
	 * 重启节点
	 *
	 * @param nodeOpsReq 节点操作请求体
	 * @return {@code BaseRsp<Boolean> }
	 */
	@ApiOperation("重启节点")
	@PostMapping("/scene/node/restart")
	@RequiresPermissions(MenuConstant.WEB_CR_SINGLE_TRAIN)
	public BaseRsp<Boolean> restartNode(@RequestBody SceneNodeOpsReq nodeOpsReq) {
		String userId = UserInfoContext.getUserContext().getUserId();
		SingleRoom room = singleRoomService
				.getByBusinessTemplateIdAndUserId(nodeOpsReq.getResourceId(), userId);
		if (room == null) {
			log.error("scene not started: ResourceId = [{}]", nodeOpsReq.getResourceId());
			throw new BusinessException(CrBusinessErrorCode.SCENE_NOT_STARTED);
		}
		Boolean result = localSceneService.restartNode(room.getLocalSceneId(),
				nodeOpsReq.getNodeId());
		if (Boolean.FALSE.equals(result)) {
			log.error("failed_to_restart_node: LocalSceneId = [{}]", room.getLocalSceneId());
			throw new BusinessException(CrBusinessErrorCode.FAILED_TO_RESTART_NODE);
		}
		return new BaseRsp<>(result);
	}

	/**
	 * 重新创建节点
	 *
	 * @param nodeOpsReq 节点操作请求体
	 * @return {@code BaseRsp<Boolean> }
	 */
	@ApiOperation("重建节点")
	@PostMapping("/scene/node/recreate")
	@RequiresPermissions(MenuConstant.WEB_CR_SINGLE_TRAIN)
	public BaseRsp<Boolean> recreateNode(@RequestBody SceneNodeOpsReq nodeOpsReq) {
		String userId = UserInfoContext.getUserContext().getUserId();
		SingleRoom room = singleRoomService
				.getByBusinessTemplateIdAndUserId(nodeOpsReq.getResourceId(), userId);
		if (room == null) {
			log.error("scene not started: ResourceId = [{}]", nodeOpsReq.getResourceId());
			throw new BusinessException(CrBusinessErrorCode.SCENE_NOT_STARTED);
		}
		Boolean result = localSceneService.recreateNode(room.getLocalSceneId(),
				nodeOpsReq.getNodeId());
		if (Boolean.FALSE.equals(result)) {
			log.error("failed_to_restart_node: LocalSceneId = [{}]", room.getLocalSceneId());
			throw new BusinessException(CrBusinessErrorCode.FAILED_TO_RECREATE_NODE);
		}
		return new BaseRsp<>(result);
	}

	/**
	 * 训练历史
	 *
	 * @param businessTemplateId 业务模板id
	 * @return {@code BaseRsp<List<TrainingRecordListBo>> }
	 */
	@ApiOperation("训练历史")
	@GetMapping("/train_history")
	@RequiresPermissions(MenuConstant.WEB_CR_SINGLE_TRAIN)
	public BaseRsp<List<TrainingRecordListBo>>
			trainHistory(@RequestParam("resource_id") String businessTemplateId) {
		return new BaseRsp<>(singleRoomService.getTrainHistory(businessTemplateId));
	}

	/**
	 * 提交训练
	 *
	 * @param requestBody 请求正文
	 * @return {@code BaseRsp<String> }
	 */
	@ApiOperation("训练提交")
	@PostMapping("/train_submit")
	@RequiresPermissions(MenuConstant.WEB_CR_SINGLE_TRAIN)
	public BaseRsp<String>
			submitTrain(@RequestBody @Validated SingleRoomTrainSubmitReq requestBody) {
		return new BaseRsp<>(singleRoomService.submitTrain(requestBody.getResourceId()));
	}

	/**
	 * 获取任务矩阵
	 *
	 * @param resourceId 资源id
	 * @return {@code BaseRsp<SingleMatrixProgressRsp> }
	 */
	@ApiOperation("获取任务矩阵和训练进度")
	@GetMapping("/mission/matrix")
	@RequiresPermissions(MenuConstant.WEB_CR_SINGLE_TRAIN)
	public BaseRsp<SingleMatrixProgressRsp> getMissionMatrix(
			@ApiParam(name = "resource_id", value = "单兵模板id", required = true) @RequestParam(
					value = "resource_id", required = true) String resourceId) {
		if (StringUtils.isBlank(resourceId)) {
			throw new BusinessException(CrBusinessErrorCode.RESOURCE_ID_MISSING);
		}
		SingleMatrixProgressRsp res = singleRoomService.getMatrixRsp(resourceId);
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
		CrCommonValidator.validateId(req.getResource_id());
		ListRsp<JSONObject> res = singleRoomService.getAssetList(req);
		return new BaseRsp<>(res);
	}

	/**
	 * 获取操作系统名字
	 * 透传中台节点数据
	 *
	 * @param resourceId 资源id
	 * @return {@code BaseRsp<List<IdNameType>>}
	 */
	@ApiOperation("操作系统列表, 需要开启场景")
	@GetMapping("/os/list")
	@RequiresPermissions(MenuConstant.WEB_CR_SINGLE_TRAIN)
	public BaseRsp<List<IdNameType>> getOsNames(
			@ApiParam(name = "resource_id", value = "单兵训练id", required = true) @RequestParam(
					value = "resource_id", required = true) String resourceId) {
		CrCommonValidator.validateId(resourceId);
		List<IdNameType> res = singleRoomService.getOsNames(resourceId);
		return new BaseRsp<>(res);
	}
}
