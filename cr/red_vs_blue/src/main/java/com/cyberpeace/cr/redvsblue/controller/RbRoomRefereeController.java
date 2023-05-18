package com.cyberpeace.cr.redvsblue.controller;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.commons.compress.utils.Lists;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.constant.MissionWsCode;
import com.cyberpeace.cr.crbase.controller.req.SubmitEvaluationReq;
import com.cyberpeace.cr.crbase.controller.rsp.MissionEvaluationRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrRole;
import com.cyberpeace.cr.crbase.dao.entity.CrTaskSubmit;
import com.cyberpeace.cr.crbase.service.CrCommonService;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationContext;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationQuery;
import com.cyberpeace.cr.crbase.service.context.evaluation.EvaluationListReq;
import com.cyberpeace.cr.crbase.service.context.evaluation.EvaluationListRsp;
import com.cyberpeace.cr.crbase.service.context.role.UserRole;
import com.cyberpeace.cr.crbase.service.context.scene.MonitorSession;
import com.cyberpeace.cr.redvsblue.controller.req.RbMissionEvaluationListReq;
import com.cyberpeace.cr.redvsblue.dao.entity.RbRoom;
import com.cyberpeace.cr.redvsblue.service.RbRefereeService;
import com.cyberpeace.cr.redvsblue.service.RbRoomService;
import com.cyberpeace.cr.redvsblue.service.context.evaluation.RbRoleIdNameType;
import com.cyberpeace.integratation.service.RemoteUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

@Api(tags = "红蓝对抗裁判")
@RestController
@RequiresAuthentication
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
	private RbRefereeService rbRefereeService;

	@ApiOperation("获取角色列表")
	@GetMapping("/roles")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<List<RbRoleIdNameType>> getRoleList(
			@ApiParam(name = "resource_id", value = "红蓝对抗id", required = true) String resource_id) {
		List<CrRole> roles = rbRoomService.getStudentsRoles(resource_id);
		List<RbRoleIdNameType> res = Lists.newArrayList();
		for (CrRole role : roles) {
			RbRoleIdNameType rbRole = new RbRoleIdNameType();
			rbRole.setId(role.getResourceId());
			rbRole.setName(role.getName());
			rbRole.setType(role.getRoleGroup());
			rbRole.setPrefix();

			res.add(rbRole);
		}
		// 红方角色放在前面
		res.sort(Comparator.comparing(RbRoleIdNameType::getType));
		return new BaseRsp<>(res);
	}

	@ApiOperation("获取学员的评估提交记录")
	@GetMapping("/evaluation/list")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<List<EvaluationListRsp>> getEvaluationList(@Validated EvaluationListReq req) {
		// 鉴权
		rbRoomService.checkRefereePermission(req.getRoomId(),
				UserInfoContext.getUserContext().getUserId());
		// get evaluation list
		List<EvaluationListRsp> res = rbRefereeService.getEvaluationList(req);
		return new BaseRsp<>(res);
	}

	/**
	 * 5.4弃用
	 * 更换为不分页的评估列表接口
	 *
	 * @param missionEvaluationListReq 任务评价列表请求体
	 * @return {@code BaseRsp<List<MissionEvaluationRsp>>}
	 */
	@ApiOperation("获取学员的评估提交记录")
	@GetMapping("/mission_evaluation")
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

	@ApiOperation("提交任务的人工评估")
	@PostMapping("/submit_evaluation")
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

		crTaskService.pushMissionWS(submitEvaluationReq.getRoomId(),
				MissionWsCode.RVB_MISSION_CODE);
		return new BaseRsp<>();
	}

	@ApiOperation("获取监控")
	@GetMapping("/sessions")
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
