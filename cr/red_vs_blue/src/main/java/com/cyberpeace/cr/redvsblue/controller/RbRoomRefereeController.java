package com.cyberpeace.cr.redvsblue.controller;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.PropertyNamingStrategy;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.component.MyWebSocketHandler;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.constant.WsPath;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.model.ws.WebSocketMessageContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.JsonUtil;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.constant.MissionWsCode;
import com.cyberpeace.cr.crbase.constant.RbSituationWsCode;
import com.cyberpeace.cr.crbase.controller.req.SubmitEvaluationReq;
import com.cyberpeace.cr.crbase.controller.req.SubmitScanInfoReq;
import com.cyberpeace.cr.crbase.controller.rsp.MissionEvaluationRsp;
import com.cyberpeace.cr.crbase.dao.entity.BusinessTemplate;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.CrTaskSubmit;
import com.cyberpeace.cr.crbase.dao.mapper.CrRoleMapper;
import com.cyberpeace.cr.crbase.service.CrCommonService;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationContext;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationQuery;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNode;
import com.cyberpeace.cr.crbase.service.context.role.CrRoleNodesData;
import com.cyberpeace.cr.crbase.service.context.role.UserRole;
import com.cyberpeace.cr.crbase.service.context.scene.MonitorSession;
import com.cyberpeace.cr.redvsblue.controller.req.RbMissionEvaluationListReq;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbMissionStatusGroupWsDataRsp;
import com.cyberpeace.cr.redvsblue.dao.entity.RbCombatLog;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.dao.entity.RbWsMessageIdentifier;
import com.cyberpeace.cr.redvsblue.service.RbCombatLogService;
import com.cyberpeace.cr.redvsblue.service.RbRoomService;
import com.cyberpeace.cr.redvsblue.service.context.MissionStatusWs;
import com.cyberpeace.cs.dao.entity.LocalScene;
import com.cyberpeace.cs.dao.mapper.LocalSceneMapper;
import com.cyberpeace.integratation.service.RemoteUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Api(tags = "红蓝对抗裁判")
@RestController
@RequestMapping("/cr/red_vs_blue/referee/")
public class RbRoomRefereeController {

	@Autowired
	private RbRoomService rbRoomService;

	@Autowired
	private CrTaskService crTaskService;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private CrCommonService crCommonService;

	@Autowired
	private RbCombatLogService rbCombatLogService;

	@Autowired
	private MyWebSocketHandler myWebSocketHandler;

	@Autowired
	private CrRoleMapper crRoleMapper;

	@Autowired
	private LocalSceneMapper localSceneMapper;

	@ApiOperation("获取学员的评估提交记录")
	@GetMapping("/mission_evaluation")
	@RequiresAuthentication
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<List<MissionEvaluationRsp>>
			getMissionEvaluations(@Validated RbMissionEvaluationListReq missionEvaluationListReq) {

		MissionEvaluationQuery missionEvaluationQuery = BeanUtils.copy(missionEvaluationListReq,
				MissionEvaluationQuery.class);
		missionEvaluationQuery.setPer_page(missionEvaluationListReq.getPerPage());
		missionEvaluationQuery.setStudentHash(missionEvaluationListReq.getSubmitUser());

		rbRoomService.checkRefereePermission(missionEvaluationListReq.getRoomId(),
				UserInfoContext.getUserContext().getUserId());

		IPage<MissionEvaluationContext> data = crTaskService
				.getRbMissionEvaluationList(missionEvaluationQuery);

		List<String> userIds = data.getRecords().stream()
				.map(MissionEvaluationContext::getSubmitUser).distinct()
				.collect(Collectors.toList());

		userIds.addAll(data.getRecords().stream().map(MissionEvaluationContext::getEvaluatorId)
				.distinct().collect(Collectors.toList()));
		Map<String, String> userInfoMap = remoteUserService.getIdNameMap(userIds);

		data.convert(missionEvaluationContext -> {
			MissionEvaluationRsp missionEvaluationRsp = BeanUtils.copy(missionEvaluationContext,
					MissionEvaluationRsp.class);

			MissionEvaluationRsp.SubmitInfo submitInfo = missionEvaluationRsp.new SubmitInfo();

			submitInfo.setCreateTime(missionEvaluationContext.getCreateTime());
			submitInfo.setUserAnswer(missionEvaluationContext.getAnswer());

			missionEvaluationRsp
					.setEvaluatorName(userInfoMap.get(missionEvaluationContext.getEvaluatorId()));
			missionEvaluationRsp.setEvaluator(missionEvaluationContext.getEvaluatorId());
			missionEvaluationRsp.setSubmitUserRole(missionEvaluationContext.getSubmitUserRole());
			missionEvaluationRsp
					.setSubmitUserRoleResourceId(missionEvaluationContext.getSubmitUserRoleId());
			missionEvaluationRsp
					.setSubmitUserName(userInfoMap.get(missionEvaluationContext.getSubmitUser()));
			missionEvaluationRsp.setSubmitLogHash(missionEvaluationContext.getMissionHash());
			missionEvaluationRsp.setTotalScore(missionEvaluationContext.getMission().getScore());
			missionEvaluationRsp.setEvaluateTime(missionEvaluationContext.getEvaluateTime());
			missionEvaluationRsp.setSubmitInfo(submitInfo);
			missionEvaluationRsp.setMissionData(missionEvaluationContext.getMission());

			return missionEvaluationRsp;
		});

		return new BaseRsp(PageUtil.getListResult(data));
	}

	/**
	 * 查询红蓝对抗学员用户列表
	 * @return
	 */
	@ApiOperation("查询红蓝对抗学员用户列表")
	@GetMapping("/get_user_list")
	@RequiresAuthentication
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<List<UserRole>> getUserList(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {
		List<UserRole> data = rbRoomService.getUserList(resource_id);

		return new BaseRsp<>(data);
	}

	/**
	 * 获取统计数据
	 * @return
	 */
	@ApiOperation("获取统计数据")
	@GetMapping("/get_statistical_info")
	@RequiresAuthentication
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<JSONObject> getStatisticalInfo(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {
		MissionEvaluationQuery missionEvaluationQuery = new MissionEvaluationQuery();
		missionEvaluationQuery.setRoomId(resource_id);
		missionEvaluationQuery.setPage(1);

		IPage<MissionEvaluationContext> data = crTaskService
				.getRbMissionEvaluationList(missionEvaluationQuery);

		Integer successMissionsCount = 0;
		Integer failMissionsCount = 0;
		Integer rejectMissionsCount = 0;
		Integer submitMissionCount = 0;
		Integer waitMissionsCount = 0;

		List<MissionEvaluationContext> taskSubmits = data.getRecords();
		for (MissionEvaluationContext taskSubmit : taskSubmits) {
			submitMissionCount++;

			if (CrTaskSubmit.EvaluateStatus.SUCCESS.getCode()
					.equals(taskSubmit.getEvaluateStatus())) {
				successMissionsCount++;
			} else if (CrTaskSubmit.EvaluateStatus.FAIL.getCode()
					.equals(taskSubmit.getEvaluateStatus())) {
				failMissionsCount++;
			} else if (CrTaskSubmit.EvaluateStatus.REJECT.getCode()
					.equals(taskSubmit.getEvaluateStatus())) {
				rejectMissionsCount++;
			} else {
				waitMissionsCount++;
			}
		}

		JSONObject result = new JSONObject();
		result.put("success_missions_count", successMissionsCount);
		result.put("fail_missions_count", failMissionsCount);
		result.put("reject_missions_count", rejectMissionsCount);
		result.put("submit_mission_count", submitMissionCount);
		result.put("wait_missions_count", waitMissionsCount);

		return new BaseRsp(result);
	}

	@ApiOperation("获取中台监听scan进程")
	@PostMapping("/submit_scan_info")
	public BaseRsp<String> updateScanInfo(@ApiParam(name = "submitScanInfoReq",
			value = "红蓝") @RequestBody SubmitScanInfoReq submitScanInfoReq) {
		RbCombatLog rbCombatLog = RbCombatLog.builder().build();
		LocalScene localScene = localSceneMapper.selectOne(LocalScene.BusinessCode.RED_VS_BLUE.getCode());
		if (localScene == null) {
			return new BaseRsp<>();
		}
		String roomId = localScene.getBusinessInstanceId();
		List<CrRole> crRoleList = crRoleMapper.getRolesByRoom(roomId,
				BusinessTemplate.BusinessType.RED_VS_BLUE.getCode());
		crRoleList = crRoleList.stream().filter(s-> s.getRoleGroup() >=4).collect(Collectors.toList());
		Integer sourceGroup;
		for(CrRole item:crRoleList){
			CrRoleNodesData crRoleNodesData = item.getNodeData();
			for (CrRoleNode crRoleNode : crRoleNodesData.getVisibleNodes()) {
				if (submitScanInfoReq.getNodeIds().contains(crRoleNode.getId())) {
					sourceGroup = item.getRoleGroup();
					RbCombatLog investigationMission = rbCombatLogService.newRecord(sourceGroup, "1_情报侦察", rbCombatLog, roomId);
					rbCombatLogService.pushMissionReferee(roomId, investigationMission, new ArrayList<>(), null);
				}
			}
		}

//		RbWsMessageIdentifier identifier = new RbWsMessageIdentifier();
//		identifier.setPath(WsPath.RB_COMBAT_LOG);
//		identifier.setRoomId(roomId);
//		MissionStatusWs data = new MissionStatusWs();
//		data.setInvestigationMission(investigationMission);
//		data.setCode(RbSituationWsCode.MISSION_STATUS_UPDATE_CODE);
//		WebSocketMessageContext messageContext = WebSocketMessageContext.builder()
//				.data(JsonUtil.toJsonString(data,
//						PropertyNamingStrategy.SnakeCase))
//				.messageIdentifier(identifier).build();
//		myWebSocketHandler.pushMessage(messageContext, true);
		return new BaseRsp<>();
	}

	@ApiOperation("提交任务的人工评估")
	@PostMapping("/submit_evaluation")
	@RequiresAuthentication
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<String> updateSubmitEvaluation(@ApiParam(name = "submitEvaluationReq",
			value = "学员评分") @RequestBody SubmitEvaluationReq submitEvaluationReq) {
		rbRoomService.checkRoomOpening(submitEvaluationReq.getRoomId());
		rbRoomService.checkRefereePermission(submitEvaluationReq.getRoomId(),
				UserInfoContext.getUserContext().getUserId());

		crTaskService.submitEvaluation(submitEvaluationReq.getSubmitId(),
				submitEvaluationReq.getScore(), submitEvaluationReq.getDetail(),
				submitEvaluationReq.getReason(), submitEvaluationReq.getEvaluateStatus(),
				UserInfoContext.getUserContext().getUserId());
		// TODO 同步攻击阶段的判定  判断情报侦察任务完成情况
		RbCombatLog investigationMission = rbCombatLogService.intelligenceInvestigationMission(submitEvaluationReq);
		//  判断该任务是否属于防御阶段
		List<RbCombatLog> defenseStageMissions = rbCombatLogService.defenseStageMission(submitEvaluationReq);
		//  判断该任务是否属于攻击阶段渗透利用
		RbCombatLog attackStageMission = rbCombatLogService.attackStageMission(submitEvaluationReq);
		RbMissionStatusGroupWsDataRsp fullScoreMissionStatus = rbCombatLogService.fullScoreMissionStatus(submitEvaluationReq);
		// 推送消息，更新大屏任务进度，任务状态，得分及得分趋势
		rbCombatLogService.pushMissionReferee(submitEvaluationReq.getRoomId(), investigationMission, defenseStageMissions, attackStageMission);
		crTaskService.pushMissionWS(submitEvaluationReq.getRoomId(),
				MissionWsCode.RVB_MISSION_CODE);
		RbWsMessageIdentifier identifier = new RbWsMessageIdentifier();
		identifier.setPath(WsPath.RB_COMBAT_LOG);
		identifier.setRoomId(submitEvaluationReq.getRoomId());
		MissionStatusWs data = new MissionStatusWs();
		data.setInvestigationMission(investigationMission);
		data.setAttackStageMission(attackStageMission);
		data.setDefenseStageMissions(defenseStageMissions);
		data.setCode(RbSituationWsCode.MISSION_STATUS_UPDATE_CODE);
		if((null != defenseStageMissions || null != attackStageMission || null != investigationMission) && null != fullScoreMissionStatus){
			data.setFullScoreMissionStatus(fullScoreMissionStatus);
		}
		WebSocketMessageContext messageContext = WebSocketMessageContext.builder()
				.data(JsonUtil.toJsonString(data,
						PropertyNamingStrategy.SnakeCase))
				.messageIdentifier(identifier).build();
		myWebSocketHandler.pushMessage(messageContext, true);
		return new BaseRsp<>();
	}

	@ApiOperation("获取监控")
	@GetMapping("/sessions")
	@RequiresAuthentication
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<List<MonitorSession>> getSessions(
			@ApiParam(name = "room_id", value = "训练实例id", required = true) String room_id) {
		RbRoom room = rbRoomService.getById(room_id);
		if (room == null || StringUtils.isBlank(room.getLocalSceneId())) {
			return new BaseRsp<>(Lists.newArrayList());
		}
		return new BaseRsp<>(crCommonService.getMonitorSessions(room.getLocalSceneId(), null));
	}

}
