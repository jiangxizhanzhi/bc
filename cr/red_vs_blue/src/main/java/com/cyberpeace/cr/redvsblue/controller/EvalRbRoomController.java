package com.cyberpeace.cr.redvsblue.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.common.util.BeanUtils;
import com.cyberpeace.common.util.PageUtil;
import com.cyberpeace.cr.crbase.controller.CrScoreController;
import com.cyberpeace.cr.crbase.controller.rsp.MissionDynamicRsp;
import com.cyberpeace.cr.crbase.controller.rsp.MissionEvaluationRsp;
import com.cyberpeace.cr.crbase.controller.rsp.StudentScoreRsp;
import com.cyberpeace.cr.crbase.dao.entity.CrTaskSubmit;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.CrTaskService;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationContext;
import com.cyberpeace.cr.crbase.service.context.MissionEvaluationQuery;
import com.cyberpeace.cr.crbase.service.context.role.UserRole;
import com.cyberpeace.cr.redvsblue.controller.req.RbMissionEvaluationListReq;
import com.cyberpeace.cr.redvsblue.controller.rsp.RbSituationScoreRsp;
import com.cyberpeace.cr.redvsblue.controller.rsp.TimeDataRsp;
import com.cyberpeace.cr.redvsblue.service.EvalRbRoomService;
import com.cyberpeace.cr.redvsblue.service.RbRoomScoreService;
import com.cyberpeace.cr.redvsblue.service.RbRoomService;
import com.cyberpeace.cr.redvsblue.service.RbSituationService;
import com.cyberpeace.cr.redvsblue.service.context.TimeData;
import com.cyberpeace.cr.redvsblue.service.context.XYData;
import com.cyberpeace.integratation.service.RemoteUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Api(tags = "红蓝对抗实例统计")
@RestController
@Slf4j
@RequiresAuthentication
@RequestMapping("/cr/eval/red_vs_blue")
public class EvalRbRoomController {


	@Autowired
	private CrTaskService crTaskService;

	@Autowired
	private RbRoomScoreService rbRoomScoreService;
	@Autowired
	private EvalRbRoomService evalRbRoomService;

	@Autowired
	private RbRoomService rbRoomService;

	@Autowired
	private CrRoleService crRoleService;

	@Autowired
	private RemoteUserService remoteUserService;

	@Autowired
	private CrScoreController crScoreController;

	@Autowired
	private RbSituationService rbSituationService;


	@ApiOperation("获取学员的评估提交记录")
	@GetMapping("/mission_evaluation")
//	@RequiresPermissions(MenuConstant.WEB_CR_EVALUATION)
	public BaseRsp<List<MissionEvaluationRsp>>
	getMissionEvaluations(@Validated RbMissionEvaluationListReq missionEvaluationListReq) {

		MissionEvaluationQuery missionEvaluationQuery = BeanUtils.copy(missionEvaluationListReq,
				MissionEvaluationQuery.class);
		missionEvaluationQuery.setPer_page(missionEvaluationListReq.getPerPage());
		missionEvaluationQuery.setStudentHash(missionEvaluationListReq.getSubmitUser());

//		rbRoomService.checkRefereePermission(missionEvaluationListReq.getRoomId(),
//				UserInfoContext.getUserContext().getUserId());

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
	 * 获取统计数据
	 * @return
	 */
	@ApiOperation("获取统计数据")
	@GetMapping("/get_statistical_info")
//	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<JSONObject> getStatisticalInfo(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {
		MissionEvaluationQuery missionEvaluationQuery = new MissionEvaluationQuery();
		missionEvaluationQuery.setRoomId(resource_id);
		missionEvaluationQuery.setPage(1);

		IPage<MissionEvaluationContext> data = crTaskService.getRbMissionEvaluationList(missionEvaluationQuery);

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

	/**
	 * 查询红蓝对抗学员用户列表
	 * @return
	 */
	@ApiOperation("查询红蓝对抗学员用户列表")
	@GetMapping("/get_user_list")
//	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<List<UserRole>> getUserList(
			@ApiParam(name = "resource_id", value = "团队训练id", required = true) String resource_id) {
		List<UserRole> data = rbRoomService.getUserList(resource_id);
		return new BaseRsp<>(data);
	}
	/**
	 * 获取动态事件
	 *
	 * @return
	 */
	@ApiOperation("获取学员动态事件")
	@GetMapping("/get_user_mission_dynamic")
//	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<MissionDynamicRsp> getUserMissionDynamic(
			@ApiParam(name = "resource_id", value = "红蓝对抗id", required = true) String resource_id,
			@ApiParam(name = "student_id", value = "用户id", required = true) String student_id) {
		rbRoomService.checkRefereePermission(resource_id,
				UserInfoContext.getUserContext().getUserId());

		MissionDynamicRsp missionDynamicRsp = crScoreController.getUserMissionDynamic(resource_id,
				student_id);

		return new BaseRsp<>(missionDynamicRsp);
	}

	@ApiOperation("查询学员评分")
	@GetMapping("/student_score")
//	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<StudentScoreRsp> getStudentScore(
			@ApiParam(name = "resource_id", value = "红蓝对抗id", required = true) String resource_id,
			@ApiParam(name = "user_hash", value = "用户id", required = true) String user_hash) {
//		rbRoomService.checkRefereePermission(resource_id,
//				UserInfoContext.getUserContext().getUserId());
       //todo  修改得分
		StudentScoreRsp studentScoreRsp = evalRbRoomService.getStudentScore(resource_id, user_hash);

		return new BaseRsp<>(studentScoreRsp);
	}
	/**
	 *  红方/蓝方成绩页面 右侧下折线图 得分趋势
	 *     队伍得分趋势统计
	 * @return
	 */
	@ApiOperation("队伍得分趋势统计")
	@GetMapping("/team_score_progress")
//	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<JSONObject> getRbScoreProgress(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id,
			@ApiParam(name = "role_type", value = "角色类型", required = true) Integer roleType) {
//		String userId = UserInfoContext.getUserContext().getUserId();
//		if (CrRole.RbRoleGroup.RED_BLUE.equals(role_type)) {
//			return new BaseRsp(getRbScoreProgress_6(resource_id, roleType, userId));
//		}
//		permissionCheckTeacherOrGroup(resource_id, userId, roleType);
		//todo  修改得分
		JSONObject data = rbRoomScoreService.getRbScoreProgress(resource_id, roleType);
		return new BaseRsp(data);
	}

	/**
	 * 查询红蓝实时战况页面 正中间计时器
	 * 获取实例运行时间和状态
	 * @return
	 */
	@ApiOperation("获取实例运行时间和状态")
	@GetMapping("/rb_time_data")
//	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<TimeDataRsp> getRbTimeData(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id) {
		TimeData data = rbRoomScoreService.getRbTimeData(resource_id);

		TimeDataRsp timeDataRsp = new TimeDataRsp();
		timeDataRsp.setStartTime((int) (data.getStartTime().getTime() / 1000));
		timeDataRsp.setTimeStamp((int) (System.currentTimeMillis() / 1000));
		timeDataRsp.setRunning(data.getRunning());
		timeDataRsp.setStatus(data.getStatus());
		return new BaseRsp(timeDataRsp);
	}

	/**
	 *  红方/蓝方成绩页面 右侧中折线图 任务进展
	 *     红蓝实时战况页面 左右两侧 任务进展
	 *     队内任务下发和完成任务数进展
	 * @return
	 */
	@ApiOperation("获取任务下发和完成任务数进展")
	@GetMapping("/team_mission_progress")
//	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<JSONObject> getRbMissionProgress(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id,
			@ApiParam(name = "role_type", value = "角色类型", required = true) Integer roleType) {
//		String userId = UserInfoContext.getUserContext().getUserId();
//		permissionCheckTeacherOrGroup(resource_id, userId, roleType);
		JSONObject data = rbRoomScoreService.getRbMissionProgress(resource_id, roleType);
		return new BaseRsp(data);
	}


	/**
	 * 红蓝实时战况页面 左右两侧 任务分类 柱状图
	 *     获取实例下的角色任务分类分布数据
	 * @return
	 */
	@ApiOperation("获取实例下的角色任务分类分布数据")
	@GetMapping("/team_members_ranking")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<JSONObject> getRbMembersRanking(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id,
			@ApiParam(name = "role_type", value = "角色类型", required = true) Integer roleType) {
//		String userId = UserInfoContext.getUserContext().getUserId();

//		permissionCheckTeacherOrGroup(resource_id, userId, roleType);
  		//todo  修改得分
		JSONObject data = rbRoomScoreService.getRbMembersRanking(resource_id, roleType);
		return new BaseRsp(data);
	}

	/**
	 * 红蓝实时战况页面 左右两侧 任务分类 柱状图
	 *     获取实例下的角色任务分类分布数据
	 * @return
	 */
	@ApiOperation("获取实例下的任务评估数据统计")
	@GetMapping("/team_mission_evaluation")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<JSONObject> getRbMissionEvaluation(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id,
			@ApiParam(name = "role_type", value = "角色类型", required = true) Integer roleType) {
//		String userId = UserInfoContext.getUserContext().getUserId();
//		permissionCheckTeacherOrGroup(resource_id, userId, roleType);

		JSONObject data = rbRoomScoreService.getRbMissionEvaluation(resource_id, roleType);
		return new BaseRsp(data);
	}

	/**
	 * 红蓝实时战况页面 左右两侧 任务分类 柱状图
	 *     获取实例下的角色任务分类分布数据
	 * @return
	 */
	@ApiOperation("获取实例下的角色任务分类分布数据")
	@GetMapping("/team_mission_category")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<XYData> getRbMissionCategory(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id,
			@ApiParam(name = "role_type", value = "角色类型", required = true) Integer roleType) {
//		String userId = UserInfoContext.getUserContext().getUserId();
//
//		permissionCheckTeacherOrGroup(resource_id, userId, roleType);

		XYData data = rbRoomScoreService.getRbMissionCategory(resource_id, roleType);
		return new BaseRsp(data);
	}


	/**
	 * 指定队伍（红/蓝）排行榜 得分排名 任务进展排名
	 * @return
	 */
	@ApiOperation("指定队伍（红/蓝）排行榜 得分排名")
	@GetMapping("/team_leader_board")
//	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<JSONObject> getRbLeaderBoard(
			@ApiParam(name = "resource_id", value = "训练id", required = true) String resource_id,
			@ApiParam(name = "role_type", value = "角色类型", required = true) Integer roleType) {
//		String userId = UserInfoContext.getUserContext().getUserId();
//
//		permissionCheckTeacherOrGroup(resource_id, userId, roleType);
       //todo  修改得分
		JSONObject data = rbRoomScoreService.getRbLeaderBoard(resource_id, roleType);

		return new BaseRsp(data);
	}


	@ApiOperation("获取红蓝对抗队伍得分")
	@GetMapping("/score")
	public BaseRsp<RbSituationScoreRsp> score(
			@ApiParam(value = "红蓝对抗实例Id", required = true) @RequestParam(value = "room_id") String roomId,
			@ApiParam(value = "角色类型, 红=4;蓝=5", required = true) @RequestParam(value = "role_type") Integer roleType) {
		//todo  修改得分
		return new BaseRsp<>(rbSituationService.getTeamScore(roomId, roleType));
	}

}
