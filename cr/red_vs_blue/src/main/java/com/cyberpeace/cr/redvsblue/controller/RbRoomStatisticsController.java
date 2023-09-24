package com.cyberpeace.cr.redvsblue.controller;

import java.util.List;
import java.util.Map;

import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.cyberpeace.common.constant.MenuConstant;
import com.cyberpeace.common.entity.CrRole;
import com.cyberpeace.common.enums.RbRoleGroup;
import com.cyberpeace.common.exception.BusinessException;
import com.cyberpeace.common.model.BaseRsp;
import com.cyberpeace.common.model.user.UserInfoContext;
import com.cyberpeace.cr.crbase.constant.CrBusinessErrorCode;
import com.cyberpeace.cr.crbase.service.CrRoleService;
import com.cyberpeace.cr.crbase.service.context.TaskCounter;
import com.cyberpeace.cr.crbase.service.context.mission.MissionsStatistic;
import com.cyberpeace.cr.redvsblue.controller.rsp.TimeDataRsp;
import com.cyberpeace.cr.redvsblue.service.RbRoomScoreService;
import com.cyberpeace.cr.redvsblue.service.RbRoomService;
import com.cyberpeace.cr.redvsblue.service.context.TimeData;
import com.cyberpeace.cr.redvsblue.service.context.XYData;
import com.cyberpeace.integratation.service.RemoteUserService;
import com.google.common.collect.Lists;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;

@Api(tags = "红蓝对抗实例统计")
@RestController
@Slf4j
@RequiresAuthentication
@RequestMapping("/cr/red_vs_blue/statistics/")
public class RbRoomStatisticsController {

	@Autowired
	private RbRoomScoreService rbRoomScoreService;

	@Autowired
	private RbRoomService rbRoomService;

	@Autowired
	private CrRoleService crRoleService;

	@Autowired
	private RemoteUserService remoteUserService;

	/**
	 * 查询红蓝实时战况页面 正中间计时器
	 * 获取实例运行时间和状态
	 * @return
	 */
	@ApiOperation("获取实例运行时间和状态")
	@GetMapping("/rb_time_data")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<TimeDataRsp> getRbTimeData(
			@ApiParam(name = "resource_id", value = "训练id", required = true) @RequestParam(
					value = "resource_id", required = true) String resourceId) {
		TimeData data = rbRoomScoreService.getRbTimeData(resourceId);

		TimeDataRsp timeDataRsp = new TimeDataRsp();
		timeDataRsp.setStartTime((int) (data.getStartTime().getTime() / 1000));
		timeDataRsp.setTimeStamp((int) (System.currentTimeMillis() / 1000));
		timeDataRsp.setRunning(data.getRunning());
		timeDataRsp.setStatus(data.getStatus());

		return new BaseRsp<>(timeDataRsp);
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
			@ApiParam(name = "resource_id", value = "训练id", required = true) @RequestParam(
					value = "resource_id", required = true) String resourceId,
			@ApiParam(name = "role_type", value = "角色类型", required = true) @RequestParam(
					value = "role_type", required = true) Integer roleType) {
		String userId = UserInfoContext.getUserContext().getUserId();

		permissionCheckTeacherOrGroup(resourceId, userId, roleType);

		XYData data = rbRoomScoreService.getRbMissionCategory(resourceId, roleType);

		return new BaseRsp<>(data);
	}

	/**
	 * 指定队伍（红/蓝）排行榜 得分排名 任务进展排名
	 * @return
	 */
	@ApiOperation("指定队伍（红/蓝）排行榜 得分排名")
	@GetMapping("/team_leader_board")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<JSONObject> getRbLeaderBoard(
			@ApiParam(name = "resource_id", value = "训练id", required = true) @RequestParam(
					value = "resource_id", required = true) String resourceId,
			@ApiParam(name = "role_type", value = "角色类型", required = true) @RequestParam(
					value = "role_type", required = true) Integer roleType) {
		String userId = UserInfoContext.getUserContext().getUserId();

		permissionCheckTeacherOrGroup(resourceId, userId, roleType);

		JSONObject data = rbRoomScoreService.getRbLeaderBoard(resourceId, roleType);

		return new BaseRsp<>(data);
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
			@ApiParam(name = "resource_id", value = "训练id", required = true) @RequestParam(
					value = "resource_id", required = true) String resourceId,
			@ApiParam(name = "role_type", value = "角色类型", required = true) @RequestParam(
					value = "role_type", required = true) Integer roleType) {
		String userId = UserInfoContext.getUserContext().getUserId();

		permissionCheckTeacherOrGroup(resourceId, userId, roleType);

		JSONObject data = rbRoomScoreService.getRbMembersRanking(resourceId, roleType);

		return new BaseRsp<>(data);
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
			@ApiParam(name = "resource_id", value = "训练id", required = true) @RequestParam(
					value = "resource_id", required = true) String resourceId,
			@ApiParam(name = "role_type", value = "角色类型", required = true) @RequestParam(
					value = "role_type", required = true) Integer roleType) {
		String userId = UserInfoContext.getUserContext().getUserId();

		permissionCheckTeacherOrGroup(resourceId, userId, roleType);

		JSONObject data = rbRoomScoreService.getRbMissionEvaluation(resourceId, roleType);

		return new BaseRsp<>(data);
	}

	/**
	 * 查询任务状态
	 * 导演-任务编排 左侧柱状图
	 * @return
	 */
	@ApiOperation("查询任务状态")
	@GetMapping("/missions_statistics")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<List<MissionsStatistic>> getMissionsStatistics(
			@ApiParam(name = "resource_id", value = "训练id", required = true) @RequestParam(
					value = "resource_id", required = true) String resourceId) {
		List<MissionsStatistic> missionsStatistics = Lists.newArrayList();
		List<String> allUserIds = Lists.newArrayList();

		List<CrRole> crRoles = crRoleService.selectRbStudentsRoleByRoomId(resourceId);
		for (CrRole crRole : crRoles) {
			List<String> userIds = crRole.getUserIdList();

			allUserIds.addAll(userIds);

			for (String userId : userIds) {
				TaskCounter taskCounter = crRoleService.getTaskCounter(resourceId, userId);
				MissionsStatistic missionsStatistic = new MissionsStatistic(crRole.getRoleGroup(),
						userId, taskCounter);
				missionsStatistics.add(missionsStatistic);
			}
		}

		Map<String, String> idNameMap = remoteUserService.getIdNameMap(allUserIds);
		missionsStatistics.forEach(missionsStatistic -> missionsStatistic
				.setUserName(idNameMap.get(missionsStatistic.getUserResourceId())));

		return new BaseRsp<>(missionsStatistics);
	}

	/**
	 *  红方/蓝方成绩页面 右侧中折线图 任务进展
	 *     红蓝实时战况页面 左右两侧 任务进展
	 *     队内任务下发和完成任务数进展
	 * @return
	 */
	@ApiOperation("获取任务下发和完成任务数进展")
	@GetMapping("/team_mission_progress")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<JSONObject> getRbMissionProgress(
			@ApiParam(name = "resource_id", value = "训练id", required = true) @RequestParam(
					value = "resource_id", required = true) String resourceId,
			@ApiParam(name = "role_type", value = "角色类型", required = true) @RequestParam(
					value = "role_type", required = true) Integer roleType) {
		String userId = UserInfoContext.getUserContext().getUserId();

		permissionCheckTeacherOrGroup(resourceId, userId, roleType);

		JSONObject data = rbRoomScoreService.getRbMissionProgress(resourceId, roleType);

		return new BaseRsp<>(data);
	}

	/**
	 *  红方/蓝方成绩页面 右侧下折线图 得分趋势
	 *     队伍得分趋势统计
	 * @return
	 */
	@ApiOperation("队伍得分趋势统计")
	@GetMapping("/team_score_progress")
	@RequiresPermissions(MenuConstant.WEB_CR_RED_VS_BLUE)
	public BaseRsp<JSONObject> getRbScoreProgress(
			@ApiParam(name = "resource_id", value = "训练id", required = true) @RequestParam(
					value = "resource_id", required = true) String resourceId,
			@ApiParam(name = "role_type", value = "角色类型", required = true) @RequestParam(
					value = "role_type", required = true) Integer roleType) {
		String userId = UserInfoContext.getUserContext().getUserId();

		if (RbRoleGroup.RED_BLUE.matches(roleType)) {
			return new BaseRsp<>(getRbScoreProgress_6(resourceId, roleType, userId));
		}

		permissionCheckTeacherOrGroup(resourceId, userId, roleType);

		JSONObject data = rbRoomScoreService.getRbScoreProgress(resourceId, roleType);

		return new BaseRsp<>(data);
	}

	/**
	 *  红蓝实时战况页面 正下方 红蓝得分
	 * @return
	 */
	private JSONObject getRbScoreProgress_6(String resourceId, Integer roleType, String userId) {

		rbRoomService.checkRoomPermission(resourceId, userId);

		return rbRoomScoreService.getAllRbScoreProgress(resourceId, roleType);
	}

	private void permissionCheckTeacherOrGroup(String resourceId, String userId, Integer roleType) {
		CrRole crRole = rbRoomService.checkRoomPermission(resourceId, userId);

		if (crRole.isRvbStudent() && !crRole.getRoleGroup().equals(roleType)) {
			log.error("user does not have RbRoom Permission！roomId=[{}], userId=[{}]", resourceId,
					userId);
			throw new BusinessException(CrBusinessErrorCode.PERMISSION_DENIED);
		}
	}
}
